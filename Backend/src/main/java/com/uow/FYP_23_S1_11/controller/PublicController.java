package com.uow.FYP_23_S1_11.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import com.stripe.model.Token;
import com.stripe.param.AccountCreateParams;
import com.stripe.param.AccountLinkCreateParams;
import com.stripe.param.AccountCreateParams.BusinessProfile;
import com.stripe.param.AccountCreateParams.Capabilities;
import com.stripe.param.AccountLinkCreateParams.Collect;
import com.uow.FYP_23_S1_11.domain.Specialty;
import com.uow.FYP_23_S1_11.service.SpecialtyService;

@RestController
@RequestMapping(value = "/api/public", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PublicController {
        @Autowired
        private SpecialtyService specialtyService;

        @GetMapping("/getAllSpecialty")
        public ResponseEntity<List<Specialty>> getAllSpecialty() {
                return specialtyService.getAllSpecialty();
        }

        @GetMapping("/test")
        public Object test() throws StripeException, FileNotFoundException {
                Stripe.apiKey = "sk_test_51N4bSsFQCAAg86VcgsQgzqPyYrlwJEXlOi7DPRdfaLYiFVIN6X1g7mzEc1Zd7pfrXJ4kuisrHGIAN0MRe4w28VsX00OxRkXLpz";

                Map<String, Object> card = new HashMap<>();
                card.put("currency", "sgd");
                card.put("number", "2227200000000009");
                card.put("exp_month", 5);
                card.put("exp_year", 2024);
                card.put("cvc", "314");

                Map<String, Object> cardDetails = new HashMap<>();
                cardDetails.put("card", card);

                Token token = Token.create(cardDetails);

                Map<String, Object> individual = new HashMap<>();
                individual.put("first_name", "Jane");
                individual.put("last_name", "Doe");

                Map<String, Object> dob = new HashMap<>();
                dob.put("day", Integer.valueOf(1).longValue());
                dob.put("month", Integer.valueOf(1).longValue());
                dob.put("year", Integer.valueOf(1901).longValue());
                individual.put("dob", dob);

                individual.put("nationality", "SG");
                individual.put("id_number", "00000000");
                individual.put("phone", "+6500000000");

                Map<String, Object> addressDetails = new HashMap<>();
                addressDetails.put("line1", "abcdef");
                addressDetails.put("postal_code", "444444");

                individual.put("address", addressDetails);
                individual.put("email", "email@example.com");

                String[] full_name_alias = new String[] { "Jane Doe" };
                individual.put("full_name_aliases", full_name_alias);

                Map<String, Object> accountDetails = new HashMap<>();
                accountDetails.put("business_type", "individual");
                accountDetails.put("individual", individual);
                accountDetails.put("tos_shown_and_accepted", true);

                Map<String, Object> accountParam = new HashMap<>();
                accountParam.put("account", accountDetails);

                Token accToken = Token.create(accountParam);

                AccountCreateParams params = AccountCreateParams.builder()
                                .setType(AccountCreateParams.Type.CUSTOM)
                                .setCapabilities(Capabilities
                                                .builder()
                                                .setCardPayments(Capabilities.CardPayments
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .setTransfers(Capabilities.Transfers
                                                                .builder()
                                                                .setRequested(true)
                                                                .build())
                                                .build())
                                .setCountry("SG")
                                .setBusinessProfile(BusinessProfile
                                                .builder()
                                                .setMcc("8062")
                                                .setProductDescription("Help to check on patients")
                                                .build())
                                .setExternalAccount(token.getId())
                                .setAccountToken(accToken.getId())
                                .build();

                Account account = Account.create(params);

                AccountLinkCreateParams params2 = AccountLinkCreateParams.builder()
                                .setAccount(account.getId())
                                .setRefreshUrl("https://example.com/reauth")
                                .setReturnUrl("https://example.com/return")
                                .setType(AccountLinkCreateParams.Type.ACCOUNT_ONBOARDING)
                                .setCollect(Collect.EVENTUALLY_DUE)
                                .build();

                AccountLink accountLink = AccountLink.create(params2);
                return accountLink.toJson();
        }

}
