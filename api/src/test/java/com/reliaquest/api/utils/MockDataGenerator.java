package com.reliaquest.api.utils;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.util.CollectionUtils;

public class MockDataGenerator {

    public List<Employee> getEmployeeMockDataList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(
                UUID.fromString("45b7a6ca-8f24-4be6-b7ec-eff5e6a2b49f"),
                "Mr. Shalon Considine",
                360992,
                35,
                "Regional Producer",
                "hatity@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("042df62f-b0fb-4e83-b6fc-73cfccce6559"),
                "Hobert Lang",
                210634,
                69,
                "Legacy Administration Executive",
                "cardguard@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("877e3d64-d9c7-46c1-ae23-02d6b02b0d05"),
                "Valery Bayer",
                89035,
                57,
                "Legacy Manufacturing Coordinator",
                "namfix@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("adcd4c6c-148f-483a-9176-97522c34f386"),
                "Marian Wehner",
                72531,
                46,
                "Corporate Retail Analyst",
                "ronstring@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("51c9696f-bcad-4c0f-87b6-1a26ff9edcd3"),
                "Johnathan Howell Sr.",
                386436,
                24,
                "Corporate Associate",
                "lotstring@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("61ca5798-b946-4dba-b865-74401107c997"),
                "Miss Val Stracke",
                59949,
                36,
                "Product Hospitality Administrator",
                "magik_mike@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("b535886c-6055-418c-9da3-cbaa9d03c4f8"),
                "Mable Hyatt",
                231641,
                31,
                "Government Planner",
                "vagram@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("f830afa9-3e02-4e5b-bb41-c57915f3ea4a"),
                "Jack VonRueden",
                291952,
                17,
                "Central Community-Services Strategist",
                "sonair@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("929bbe82-d410-445b-abde-15bc7a0bd1a2"),
                "Miss Florida Mohr",
                367872,
                22,
                "Global Sales Liaison",
                "cookley@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("f9cf0b5d-be83-417f-bac6-9f65d61739c2"),
                "Charleen Malcolm",
                428659,
                28,
                "Administration Coordinator",
                "tampflex@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("194631de-3661-4257-89ee-41458a8edbe8"),
                "Jake Ondricka II",
                357389,
                22,
                "Principal Accounting Facilitator",
                "bitchip@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("756c3073-62fd-4d65-96bc-2f863a669e98"),
                "Lue Satterfield",
                462868,
                43,
                "IT Coordinator",
                "tori_the_taurus@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("e6b91446-adce-444a-a5a5-edb757f429be"),
                "Janyce Hegmann",
                188495,
                30,
                "Accounting Director",
                "span@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("086ee66b-194d-4081-8efe-766f9cc7ae8e"),
                "Dennise Armstrong",
                367082,
                68,
                "Farming Specialist",
                "ronstring@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("5e4744b6-3367-4b9a-82e9-9f0b239f013b"),
                "Teena Miller",
                33589,
                32,
                "District Healthcare Consultant",
                "blade_runnerz@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("79d085f1-3d05-4589-9bb7-491670eecb32"),
                "Malcolm Pouros",
                313509,
                45,
                "Farming Agent",
                "keylex@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("071ab7b2-6b31-426c-afed-7d6e6f42195f"),
                "Lane Swaniawski",
                235636,
                28,
                "International Banking Agent",
                "livlaughlo@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("c0ff3611-93dd-47e4-845b-b7895cef599e"),
                "Tasha Carter",
                259508,
                49,
                "Farming Director",
                "wrapsafe@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("29bd1d5a-19ae-4fa6-af98-f0f99b5aa42b"),
                "Colin Schiller",
                339634,
                25,
                "Retail Engineer",
                "latlux@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("2444d616-d25a-49d3-905b-bf57d8039cdc"),
                "Sudie O'Reilly III",
                222574,
                17,
                "Dynamic Supervisor",
                "fat_kyle@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("a73637a6-06a6-454b-b432-c2bab0c45d18"),
                "Franklyn Bernier",
                242860,
                65,
                "Investor Associate",
                "bitwolf@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("e43b4106-e4fe-4caf-9954-cc764dc832d0"),
                "Malcolm McClure",
                159772,
                36,
                "Marketing Officer",
                "overhold@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("5e30da9f-0edf-440c-8f46-a04a60f0ae3a"),
                "Jackson Hills",
                281443,
                64,
                "Human Executive",
                "bitchip@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("2165a6a8-d1c5-4e9d-9208-cd1e6a2d1160"),
                "Ms. Marcelle Quitzon",
                249667,
                17,
                "Forward Marketing Officer",
                "zathin@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("3fec47ac-c4c3-4e69-97e5-d0b3c1a1e4c2"),
                "Karoline Shields MD",
                472183,
                18,
                "Manufacturing Planner",
                "tampflex@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("14ea933c-d432-4dcd-a9f5-463152c67610"),
                "Dolores Barrows",
                446282,
                44,
                "Senior Strategist",
                "colonelkickass@company.com"));
        employeeList.add(new Employee(
                UUID.fromString("b5e9226a-bf31-4c0d-8468-524933e4c120"),
                "Patrick Mitchell",
                33273,
                63,
                "Real-Estate Associate",
                "teejay_thompson@company.com"));
        return employeeList;
    }

    public Employee getEmployee() {
        MockDataGenerator mockDataGenerator = new MockDataGenerator();
        List<Employee> employeeList = mockDataGenerator.getEmployeeMockDataList();
        if (!CollectionUtils.isEmpty(employeeList))
            return mockDataGenerator.getEmployeeMockDataList().get(0);
        return null;
    }

    public EmployeeInput getEmployeeInput() {
        return new EmployeeInput("Patrick Mitchell", 33273, 63, "Real-Estate Associate");
    }
}
