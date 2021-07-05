package com.consoleApp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class LoginSystem {
    private String userEmail;
    private String password;
    private String filepath;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getPassword() {
        return password;
    }

    boolean isUserLogin() {
        filepath = "user.json";
        return checkFile(filepath);
    }

    boolean isAdminLogin() {
        filepath = "admin.json";
        return checkFile(filepath);
    }

    boolean validateUserID() {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getUserEmail());
        return matcher.matches();
    }

    boolean validatePassword() {
        if (!(getPassword().length() >= 8 && getPassword().length() <= 15)) {
            return false;
        }
        boolean flag = false;
        for (int i = 0; i <= 9; i++) {
            String str1 = Integer.toString(i);
            if (getPassword().contains(str1)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return false;
        }
        if (!(getPassword().contains("@") || getPassword().contains("#") || getPassword().contains("!") || getPassword().contains("~")
                || getPassword().contains("$") || getPassword().contains("%") || getPassword().contains("^") || getPassword().contains("&")
                || getPassword().contains("*") || getPassword().contains("(") || getPassword().contains(")") || getPassword().contains("-")
                || getPassword().contains("+") || getPassword().contains("/") || getPassword().contains(":") || getPassword().contains(".")
                || getPassword().contains(", ") || getPassword().contains("<") || getPassword().contains(">") || getPassword().contains("?")
                || getPassword().contains("|"))) {
            return false;
        }
        flag = false;
        for (int i = 65; i <= 90; i++) {
            char c = (char) i;
            String str1 = Character.toString(c);
            if (getPassword().contains(str1)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return false;
        }
        flag = false;
        for (int i = 90; i <= 122; i++) {
            char c = (char) i;
            String str1 = Character.toString(c);
            if (getPassword().contains(str1)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    boolean signUp() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        Object object;
        filepath = "user.json";

        if (checkFile(filepath)) {
            return false;
        }
        try {
            FileReader file = new FileReader(filepath);
            object = jsonParser.parse(file);
            jsonArray = (JSONArray) object;
            file.close();
        } catch (Exception ex) {
            System.out.println("Error occurred");
        }
        jsonObject.put("User ID", getUserEmail());
        jsonObject.put("Password", getPassword());
        jsonArray.add(jsonObject);
        try {
            FileWriter writer = new FileWriter(filepath);
            writer.write(jsonArray.toJSONString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    boolean checkFile(String filepath) {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray;
        JSONParser jsonParser = new JSONParser();
        Object object;
        boolean flag = false;
        try {
            FileReader file = new FileReader(filepath);
            object = jsonParser.parse(file);
            jsonArray = (JSONArray) object;
            jsonObject.put("User ID", getUserEmail());
            jsonObject.put("Password", getPassword());
            for (Object o : jsonArray) {
                if (jsonObject.equals(o)) {
                    flag = true;
                    break;
                }
            }
            file.close();
        } catch (Exception ex) {
            System.out.println("Error occurred");
        }
        return flag;
    }

}
