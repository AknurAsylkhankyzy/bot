package com.myspring;

import com.myspring.models.Rates;
import com.myspring.beans.RatesBean;
import com.myspring.repositories.RatesRepository;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {
    public static void main(String args[]){
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        }catch (TelegramApiRequestException e){
            e.printStackTrace();
        }
    }

    private static RateToDBThread rateToDBThread;

    public void sendMsg(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            sendMessage(sendMessage);

        }catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public String getMessage(Update update){
        Message message = update.getMessage();
        return message.getText();
    }


    public void onUpdateReceived(Update update) {
        Rates rate = new Rates();
        RatesBean ratesBean = null;
        RatesRepository ratesRepository = null;
        Message message = update.getMessage();
        System.out.println("Message received: ");
        if (message != null && message.hasText()) {
            String s = message.getText();
            if (s.equals("/help")) {
                sendMsg(message, "Вам нужно написать заглавными буквами первые три буквы нужной валюты латиницей как указано в клавиатуре!)");
            }else {
                RateToDBThread rateToDBThread1 = new RateToDBThread( this, rate, s,message);
                if(rateToDBThread!=null){
                    rateToDBThread.running = false;
                }
                rateToDBThread = rateToDBThread1;
                rateToDBThread1.start();
            }
        }
    }

    public void setButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("KZT"));
        keyboardFirstRow.add(new KeyboardButton("EUR"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public String getBotUsername() {
        return "MyTestExchangeRatesBot";
    }

    public String getBotToken() {
        return "736630693:AAH9GH7SGTkkXAN5JVFqTpo6sIXrFgoQKJg";
    }
}
