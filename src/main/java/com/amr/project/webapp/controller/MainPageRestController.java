package com.amr.project.webapp.controller;

import com.amr.project.model.dto.MainPageDto;
import com.amr.project.service.abstracts.MainPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(tags = "Контроллер главной страницы")
public class MainPageRestController {

    private final MainPageService mainPageService;

    public MainPageRestController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }
    @ApiOperation(value = "Получить все категории, популярные товары и магазины (4шт)")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "OK")
            }
    )
    @GetMapping
    public ResponseEntity<MainPageDto> findMainPage() {
        MainPageDto mainPageDto = mainPageService.getMainPageDto();
        return new ResponseEntity<>(mainPageDto, HttpStatus.OK);
    }
}
