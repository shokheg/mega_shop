package com.amr.project.webapp.controller;

import com.amr.project.converter.SalesHistoryConverter;
import com.amr.project.model.dto.report.GrandSalesDto;
import com.amr.project.model.entity.report.SalesHistory;
import com.amr.project.service.abstracts.SalesHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@Api(tags = "Контроллер работы с отчетами по продажам")
public class SalesHistoryRestController {

    public final SalesHistoryService salesHistoryService;


    public SalesHistoryRestController(SalesHistoryService salesHistoryService) {
        this.salesHistoryService = salesHistoryService;
    }

    @GetMapping("{shopId}/{fromDate}/{toDate}")
    @ApiOperation(value = "Получить отчет по продажам для всех товаров магазина за период." +
                            "Даты представены ввиде строки 'yyyy-MM-dd'")

    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK"),
                    @ApiResponse(code = 204, message = "Информации не найдено.")
            }
    )
    public ResponseEntity<GrandSalesDto> getSalesHistoryByDate(@PathVariable("shopId") Long shopId,
                                                               @PathVariable("fromDate") String fromDate,
                                                               @PathVariable("toDate") String toDate) {
        GrandSalesDto grandSalesDto = salesHistoryService.findByDates(fromDate,toDate,shopId);
        return new ResponseEntity<>(grandSalesDto, HttpStatus.OK);
    }
}
