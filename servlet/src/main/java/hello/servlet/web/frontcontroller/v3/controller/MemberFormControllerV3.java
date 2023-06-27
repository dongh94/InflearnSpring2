package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramMap) {
        //Modelviews : view를 논리이름으로 찾을 수 있도록 내보낸다. 추후 model -> view 처리
        return new ModelView("new-form");
    }
}
