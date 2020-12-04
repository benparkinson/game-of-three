package com.parkinson.ben.gameofthree.component.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.parkinson.ben.gameofthree.component.controller")
public class ControllerContext {
}
