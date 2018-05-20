package jvs.spring.picmicro.BotTelegram;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendDocument;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Esteganografia extends TelegramLongPollingBot{
	 String textoEncriptar = "";
	 List<PhotoSize> photos = null;
	 BufferedImage image;
	 
	 
	// @Override
	 

/*	public void onUpdateReceived_TEXT (Update update) {

            //check if the update has a message
            if(update.hasMessage()){
                    Message message = update.getMessage();

                    //check if the message has text. it could also  contain for example a location ( message.hasLocation() )
                    if(message.hasText()){

                            //create a object that contains the information to send back the message
                            SendMessage sendMessageRequest = new SendMessage();
                            sendMessageRequest.setChatId(message.getChatId().toString()); //who should get the message? the sender from which we got the message...
                            sendMessageRequest.setText("Recibido_mensaje : " + message.getText());
                           

       	            
                            try {
                                    sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
                            } catch (TelegramApiException e) {
                                    //do some error handling
                            }
                    }
            }

    }

*/	
        
	 public void onUpdateReceived (Update update) {
		
		 
		// We check if the update has a message and the message has text
	        if (update.hasMessage() && update.getMessage().hasText()) {
	            // Set variables
	            String message_text = update.getMessage().getText();
	            
	            long chat_id = update.getMessage().getChatId();
	            if (message_text.equals("/inicio")) {
	                SendMessage message = new SendMessage() // Create a message object object
	                        .setChatId(chat_id)
	                        .setText("Esteganografía. Seleccione opción");
	                // Create ReplyKeyboardMarkup object
	                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
	                // Create the keyboard (list of keyboard rows)
	                List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
	                // Create a keyboard row
	                KeyboardRow row = new KeyboardRow();

	                row.add("Encripta");
	                
	                keyboard.add(row);
	                row = new KeyboardRow();
	                row.add("Desencripta");

	                keyboard.add(row);
	                keyboardMarkup.setKeyboard(keyboard);
	                // Add it to the message
	                message.setReplyMarkup(keyboardMarkup);
	                try {
	                    execute(message); // Sending our message object to user
	                } catch (TelegramApiException e) {
	                    e.printStackTrace();
	                }
	            } else if (message_text.equals("Encripta")) {
	            	 
	            	 String textRet ="";
	           		 if(photos==null) {
	           			 textRet += "- Imagen: (falta)";
	           		 }else {
	           			 textRet += "- Imagen: OK";
	           		 }
	           		 textRet += "\n";
	           		 if(textoEncriptar=="") {
	           			 textRet += "- Texto: (falta)";
	           		 }else {
	           			 textRet += "- Imagen: " + textoEncriptar;
	           		 }
	           		 textRet += "\n";
           		 
	            	 if(photos!=null && textoEncriptar!="") {
	            		 //aqui proceso esteganografia
	            	 }else {
	            		 //aqui no hace nada
	           		 
	            	 }
	            	 SendMessage message = new SendMessage() // Create a message object object
		                        .setChatId(chat_id)
		                        .setText(textRet);

	                try {
	                	execute(message);
	                } catch (TelegramApiException e) {
	                    e.printStackTrace();
	                }
	           
	            } else {
	            	textoEncriptar = update.getMessage().getText();
	                SendMessage message = new SendMessage() // Create a message object object
	                        .setChatId(chat_id)
	                        .setText("Unknown command");
	                try {
	                    execute(message); // Sending our message object to user
	                } catch (TelegramApiException e) {
	                    e.printStackTrace();
	                }
	            }
	        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {
	            // Message contains photo
	            // Set variables
	            long chat_id = update.getMessage().getChatId();
	            photos = update.getMessage().getPhoto();
	            String sPath = getFilePath(photos.get(0));
	          
	            downloadPhotoByFilePath(sPath);
	           
	            
	            //AQUÍ ESTEGANOGRAFIA
	            
	        
                String foto = "C:\\Users\\alfon\\Pictures\\Camera Roll\\img1.jpg";
                java.io.File fotofile = new java.io.File(foto);
                try {
					sendDocUploadingAFile(chat_id,fotofile,"");
				} catch (TelegramApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                
	            /*
	          
	            String f_id = photos.stream()
	                    .findFirst()
	                    .orElse(null).getFileId();
	            int f_width = photos.stream()
	                    .findFirst()
	                    .orElse(null).getWidth();
	            int f_height = photos.stream()
	                    .findFirst()
	                    .orElse(null).getHeight();
	          
	            String caption = "file_id: " + f_id + "\nwidth: " + Integer.toString(f_width) + "\nheight: " + Integer.toString(f_height);
	            SendPhoto msg = new SendPhoto()
	                    .setChatId(chat_id)
	                    .setPhoto(f_id)
	                    .setCaption(caption);
	   
	            try {
	                sendPhoto(msg); // Call method to send the message
	            } catch (TelegramApiException e) {
	                e.printStackTrace();
	            }
	            */
	        }
	    }
	 
	 
	
	
	 
    //@Override
    public String getBotUsername() {
        return "TMIg1_bot";
    }

    @Override
    public String getBotToken() {
    	//TMI 5091...
    	//return "509183591:AAHdwgGIIAxEorn_2JHIzahbi9GvO4-s8L8";
    	
    	//DASI
    	return "563754268:AAFBjVSFsJYdJai8LVBp4BGIJ7hxtDG80Y8";
    }
    
    
    
    //***************************revisar ABAJO https://github.com/rubenlagus/TelegramBots/wiki/FAQ
    
    
    private void sendDocUploadingAFile(Long chatId, java.io.File save,String caption) throws TelegramApiException {

        SendDocument sendDocumentRequest = new SendDocument();
        sendDocumentRequest.setChatId(chatId);
        sendDocumentRequest.setNewDocument(save);
        sendDocumentRequest.setCaption(caption);
        sendDocument(sendDocumentRequest);
    }
   
    
    
    //descargar foto
    
    public java.io.File downloadPhotoByFilePath(String filePath) {
        try {
            // Download the file calling AbsSender::downloadFile method
            return downloadFile(filePath);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    public String getFilePath(PhotoSize photo) {
        Objects.requireNonNull(photo);

        if (photo.hasFilePath()) { // If the file_path is already present, we are done!
            return photo.getFilePath();
        } else {
        	 return null; // Just in case 	
        }
        
    }
    
}

