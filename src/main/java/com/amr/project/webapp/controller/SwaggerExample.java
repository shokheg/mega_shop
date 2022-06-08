package com.amr.project.webapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
//включаем контроллер в Swagger-документацию, tags - описание
//после запуска приложения переходим на страницу /swagger-ui/
//для локального сервера localhost:8088/swagger-ui/
@Api(tags = "SwaggerExampleController")
public class SwaggerExample {

    @GetMapping("/swagger")
    @ApiOperation(value = "Описание метода Get, возращает String")
    //описание кодов ответов, необязательно
    @ApiResponses(
            value = {
                    @ApiResponse(code = 100, message = "message for code 100"),
                    @ApiResponse(code = 200, message = "its OK"),
                    @ApiResponse(code = 300, message = "message for code 300"),
            }
    )
    public String getHello() {
        return "Hello swagger";
    }

    @GetMapping("/swagger/{id}")
    @ApiOperation(value = "Описание Get метода, возвращает Model по id")
    public ModelForSwagger getOneModel(@PathVariable int id) {
        return new ModelForSwagger(id, "FirstModel", 33);
    }

    @PostMapping("/swagger")
    @ApiOperation(value = "Описание Post метода контроллера, добавляем Model")
        public void addModel(@RequestBody ModelForSwagger modelForSwagger) {
    }

    @PutMapping("/swagger")
    @ApiOperation(value = "Описание Put метода контроллера, обновляем Model")
    public void updateModel(@RequestBody ModelForSwagger modelForSwagger) {
    }

    @DeleteMapping("/swagger/{id}")
    @ApiOperation(value = "Описание Delete метода контроллера, удаляем Model по id")
    public String deleteRequest(@PathVariable String id) {
        return "Model with id = " + id + " was deleted.";
    }


    //Класс для демонстрации, constructor, getters, setters - обязательны
    private class ModelForSwagger {
        private int id;
        private String name;
        private int age;


        public ModelForSwagger(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
