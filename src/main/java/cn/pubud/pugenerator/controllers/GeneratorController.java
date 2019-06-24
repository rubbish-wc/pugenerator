package cn.pubud.pugenerator.controllers;

import cn.pubud.pugenerator.entity.Generator;
import cn.pubud.pugenerator.entity.Table;
import cn.pubud.pugenerator.service.IGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Author: charleyZZZZ
 * @Date: 2019/6/24 15:20
 * @Version 1.0
 */
@Controller
@RequestMapping("/generator")
public class GeneratorController {

    @Autowired
    private IGeneratorService generatorService;

    @Autowired
    private Validator validator;

    @PostMapping("/submit")
    public String submit(Generator generator, Model model, BindingResult result) {
        validator.validate(generator, result);
        if (result.hasErrors()) {
            model.addAttribute("success", false);
            model.addAttribute("message", "代码生成出错：" + getErrorMessage(result));
            return "generator";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            generatorService.generate(generator);
            model.addAttribute("success", true);
            model.addAttribute("message", "代码生成成功");
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "代码生成出错：" + e.getMessage());
            e.printStackTrace();
        }
        return "generator";
    }

    @RequestMapping("/generate")
    public String generate(Model model) {
        return "generator";
    }

    @GetMapping("table/{tableName}")
    @ResponseBody
    public List<Table> table(@PathVariable(value = "tableName") String tableName) {
        return generatorService.getTables(tableName);
    }


    protected String getErrorMessage(Errors errors) {
        String errorMessage = null;
        ObjectError error;
        for(Iterator var5 = errors.getAllErrors().iterator(); var5.hasNext(); errorMessage = error.getCode()) {
            error = (ObjectError)var5.next();
            if (error.getDefaultMessage() != null) {
               errorMessage = error.getDefaultMessage();
            }
        }
        return errorMessage;
    }

}
