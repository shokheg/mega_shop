package com.amr.project.webapp.controller;

import com.amr.project.exception.ErrorMessage;
import com.amr.project.exception.PaymentApiException;
import com.amr.project.model.dto.OrderDto;
import com.amr.project.service.abstracts.UserService;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.exception.BadResponseException;
import com.qiwi.billpayments.sdk.exception.BillPaymentServiceException;
import com.qiwi.billpayments.sdk.model.Bill;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.in.PaymentInfo;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

/**
 * @author shokhalevich
 */

@RestController
@RequestMapping("/api/payment")
@Api(tags = "Контроллер оплаты")
public class PaymentController {


    private final UserService UserService;

    private static final String SECRET_KEY = "eyJ2ZXJzaW9uIjoiUDJQIiwiZGF0YSI6eyJwYXlpbl9tZXJjaGFudF9zaXRlX3VpZCI6Inhkajg3dS0wMCIsInVzZXJfaWQiOiI3OTgxODIyMDg4OSIsInNlY3JldCI6IjMzNWU5ZGFjMGU3ZmRlZDg5NzcyNmI2MGVkYWQ5OWVlZWY2MGY3MmQxYTlhMzlkZjE0OTI2YzE1Nzk1NjIxYzYifX0=";
    private static final String PUBLIC_KEY = "48e7qUxn9T7RyYE1MVZswX1FRSbE6iyCj2gCRwwF3Dnh5XrasNTx3BGPiMsyXQFNKQhvukniQG8RTVhYm3iPyWeHfLzP1UtGSrCf9PX4PKagcLZiQqbXyVzzcpF3SWir7KvpVzWBC19w9oVWkAvnbt8xyjGmzScmtsLs8xf7Lmq5VAqYLhEgAoQKe1Vu2";
    private static final String SUCCESS_PAGE = "https://example.com/payment/success?billId=cc961e8d-d4d6-4f02-b737-2297e51fb48e";
    private BillPaymentClient client = BillPaymentClientFactory.createDefault(SECRET_KEY);

    @Autowired
    public PaymentController(com.amr.project.service.abstracts.UserService userService) {
        UserService = userService;
    }

    @GetMapping("/cancel/{billId}")
    public ResponseEntity<String> billCancel(@PathVariable String billId) {
        client.cancelBill(billId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/full")
    public void getFullPaymentFormURI(@RequestBody OrderDto orderDto, HttpServletResponse response) throws URISyntaxException {

        String customerEmail = (UserService.findById(orderDto.getUserId())).getEmail();
        String customerPhone = (UserService.findById(orderDto.getUserId())).getPhone();

        CreateBillInfo billInfo = new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        orderDto.getTotal(),
                        Currency.getInstance("RUB")
                ),
                "comment",
                ZonedDateTime.now().plusDays(3),   //срок действия счета
                new Customer(
                        customerEmail,
                        orderDto.getUserId().toString(),
                        customerPhone
                ),
                SUCCESS_PAGE
        );

        BillResponse billResponse = null;
        try {
            billResponse = client.createBill(billInfo);
        } catch (URISyntaxException | BadResponseException e) {
            e.printStackTrace();
        }

        try {
            assert billResponse != null;
            response.sendRedirect(billResponse.getPayUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/quick") //todo прикрутить реквест параметры
    public void getQuickPaymentForm(@RequestParam HttpServletResponse response) throws IOException {
        MoneyAmount amount = new MoneyAmount(
                BigDecimal.valueOf(1.00),
                Currency.getInstance("RUB")
        );
        String billId = UUID.randomUUID().toString();
        String successUrl = "https://example.com/payment/success?billId=cc961e8d-d4d6-4f02-b737-2297e51fb48e";
        String paymentUrl = client.createPaymentForm(new PaymentInfo(PUBLIC_KEY, amount, billId, successUrl));
        response.sendRedirect(paymentUrl);
    }


    @ApiOperation(value = "Получение статуса оплаты счета по ID (qiwiId)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Статус счета получен"),
                    @ApiResponse(code = 400, message = "Счет не найден")
            }
    )
    @GetMapping("/status/{billId}")
    public ResponseEntity<BillResponse> getBillStatus(@PathVariable String billId) {
        BillResponse billResponse = null;
        try {
            billResponse = client.getBillInfo(billId);
        } catch (BillPaymentServiceException e) {
            Date date = new Date();
            throw new PaymentApiException(new ErrorMessage(
                    400,
                    date,
                    "Invoice not found",
                    "Счет с таким id в системе не зарегистрирован."));
        }
        return new ResponseEntity<>(billResponse, HttpStatus.OK);
    }

}
