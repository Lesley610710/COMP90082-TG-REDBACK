package com.example.server.controllers;

import com.example.server.entity.UserInfo;
import com.example.server.viewResponse.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.server.DAO.JsonFileOperator;

import static com.example.server.DAO.JsonFileOperator.getAllUser;

@RestController
@Slf4j // for print log
@CrossOrigin(origins = "https://tg-backend-397705.web.app")
@RequestMapping("/admin_user")
public class AdminController {
    JsonFileOperator dbOperator = new JsonFileOperator();

    @GetMapping("/userList")
    public Response list() throws JSONException, IOException {
        ArrayList<UserInfo> users = getAllUser();
        return Response.success(users);
    }

    @GetMapping("/detail/{email}")
    public Response detail(@PathVariable String email) throws IOException {
        log.info("Frontend request: search user {}", email);
        UserInfo userInfo = dbOperator.getUser(email);
        if (userInfo != null && userInfo.validUser) {
            return Response.success(userInfo);
        }
        return Response.fail();
    }


    @PostMapping("/add")
    public Response add(@RequestBody UserInfo userInfo) throws IOException { // POST 是放在HTTP的body里的
        log.info("Frontend request: add new user: name = {} password = {}", userInfo.name, userInfo.password);
        if (dbOperator.addUser(userInfo)) {
            return Response.success();
        } return Response.fail();
    }

    @PutMapping("/edit/{email}")
    public Response edit(@RequestBody UserInfo userInfo) throws IOException {
        log.info("Frontend request: edit user {}", userInfo.email);
        if (dbOperator.updateUser(userInfo)) {
            return Response.success();
        } return Response.fail();
    }

    @DeleteMapping("/delete/{email}")
    public Response delete(@PathVariable String email) throws IOException {
        log.info("Frontend request: delete user {}", email);
        if (dbOperator.deleteUser(email)) {
            return Response.success();
        } return Response.fail();
    }
}


