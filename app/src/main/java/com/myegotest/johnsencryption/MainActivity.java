package com.myegotest.johnsencryption;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //View Item Variables
    TextView characterCounter_TV;
    EditText plainText_ET;
    EditText encryptedMessage_ET;
    EditText keyInput_ET;
    EditText keyName_ET;
    Button keyClipboardButton;
    Button encryptedClipboardButton;
    Button encryptButton;
    Button decryptButton;
    Button getKeyButton;


    //Other Variables
    String convertedText = "";
    String key = "";
    String message;
    String convertedBinary;
    String keyString = "";
    String[] tempArray;
    String[] result;
    int counter = 0;
    int co;
    int i = 0;
    boolean keyGenerated = false;
    ClipboardManager clipboard;
    StringBuilder sb;
    List<String> newPositions;
    TextWatcher mTextEditorWatcher;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Initialize the clipboard **/
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        /** Initialize view item variables **/
        initializeViewItems();

        /** Set TextChangedListener**/
        setOnTextChangedListner();

        /** Add OnClickListeners to Buttons **/
        setOnClickListeners();

    }

    @Override
    public void onClick(View v) {
        if(v == keyClipboardButton){
            //Copy the current key to the clipboard
//            copyKeyToClipboard();
            Toast.makeText(MainActivity.this, "For Code analysis only, please sign NDA for working application", Toast.LENGTH_LONG).show();

        }

        if(v == encryptedClipboardButton){
            //Copy the encrypted message to the clipboard
//            copyEncryptedMessageToClipboard();
            Toast.makeText(MainActivity.this, "For Code analysis only, please sign NDA for working application", Toast.LENGTH_LONG).show();
        }

        if(v == getKeyButton){
            //Generate a new key if one hasn't already been generated
            if(!keyGenerated){
//                keyInput_ET.setText("");
//                generateKey();
//                keyGenerated = true;
                Toast.makeText(MainActivity.this, "For Code analysis only, please sign NDA for working application", Toast.LENGTH_LONG).show();
            } else {
//                Toast.makeText(MainActivity.this, "Please restart the app to generate a new key", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, "For Code analysis only, please sign NDA for working application", Toast.LENGTH_LONG).show();
            }
        }

        if(v == encryptButton){
//            startEncryption();
            Toast.makeText(MainActivity.this, "For Code analysis only, please sign NDA for working application", Toast.LENGTH_LONG).show();
        }

        if(v == decryptButton){
//            startDecryption();
            Toast.makeText(MainActivity.this, "For Code analysis only, please sign NDA for working application", Toast.LENGTH_LONG).show();
        }
    }

    private void setOnClickListeners() {
        keyClipboardButton.setOnClickListener(this);
        encryptedClipboardButton.setOnClickListener(this);
        getKeyButton.setOnClickListener(this);
        encryptButton.setOnClickListener(this);
        decryptButton.setOnClickListener(this);
    }

    /** Detect changes of message and update the number of available characters left **/
    private void setOnTextChangedListner() {
        mTextEditorWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Update the character counter based on how many characters are left available
                characterCounter_TV.setText(String.valueOf(250 - s.length()));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        plainText_ET.addTextChangedListener(mTextEditorWatcher);
    }

    /** Initialize view item variables **/
    private void initializeViewItems() {
        plainText_ET = (EditText) findViewById(R.id.plainText);
        encryptedMessage_ET = (EditText) findViewById(R.id.EncryptedMessage);
        keyInput_ET = (EditText) findViewById(R.id.key);
        keyName_ET = (EditText) findViewById(R.id.keyName);
        encryptButton = (Button) findViewById(R.id.encryptButton);
        decryptButton = (Button) findViewById(R.id.decryptButton);
        getKeyButton = (Button)findViewById(R.id.keyGenerator);
        keyClipboardButton = (Button) findViewById(R.id.keyClipboard);
        encryptedClipboardButton = (Button) findViewById(R.id.encryptedClipboard);
        characterCounter_TV = (TextView)findViewById(R.id.characterCounter);
    }

    /** Starts the encryption proccess **/
    private void startEncryption() {
        if( keyInput_ET.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "Please enter a key", Toast.LENGTH_LONG).show();
        } else if ( Integer.parseInt(characterCounter_TV.getText().toString()) < 0 ){
            Toast.makeText(MainActivity.this, "You have too many characters - Maximum 250 character", Toast.LENGTH_LONG).show();
        } else if (!isLetter(plainText_ET.getText().toString())){
            Toast.makeText(MainActivity.this, "Please enter only letters, no numbers or punctuation", Toast.LENGTH_LONG).show();
        } else {

            counter = 0;
            String removeComma = "";
            message = plainText_ET.getText().toString().toUpperCase() + "    ";
            String[] result = message.split("");
            boolean firstloop = true;
            convertedText = "";

            for (String intValue : result) {
                if (firstloop){

                    //Converts each individual letter into binary code
                    convertedText += convertText(intValue);
                    firstloop = false;

                } else {

                    //Converts each individual letter into binary code
                    convertedText += convertText(intValue);
                    counter ++;

                    //After every 5 letters have been converted, encryptButton group of 5 letters
                    if( counter == 5 ){
                        sb = new StringBuilder();
                        sb.append(encryptBinary(convertedText));
                        convertedText = "";
                        counter = 0;
                        removeComma += sb.toString();
                        encryptedMessage_ET.setText(splitEncryptedAndCompressedBinary(removeComma).substring(2));
                    }
                }

            }
            plainText_ET.setText("");
        }
    }

    /** Starts the decryption proccess **/
    private void startDecryption() {
        convertedBinary = "";
        counter = 0;
        String textfieldmessage = "";

        //Get encrypted message from text field
        if(encryptedMessage_ET.getText().toString().equals("")){
            Toast.makeText(MainActivity.this, "No data to decryptButton", Toast.LENGTH_LONG).show();
        } else {
            String ntextfieldmessage = encryptedMessage_ET.getText().toString().trim();

            //Find out what the first 20 leters of the string is
            if(ntextfieldmessage.length() >= 20){
                String[] parts = {ntextfieldmessage.substring(0, 20), ntextfieldmessage.substring(ntextfieldmessage.length())};
                if(parts[0].equals("***Curb Encrypted***")){
                    textfieldmessage = ntextfieldmessage.substring(20);
                } else {
                    textfieldmessage = ntextfieldmessage;
                }
            } else {
                Toast.makeText(MainActivity.this, "No data to decryptButton", Toast.LENGTH_LONG).show();
                return;
            }

            //get compressed binary and decompress it
            String[] string1;
            string1 = textfieldmessage.split(", ");
            String message1 = "";
            for(int i = 0; i < string1.length; i++){
                message1 += string1[i];
            }
            String[] string2;
            string2 = message1.split("");
            String message2 = "";
            for(int i = 0; i < string2.length; i++){
                message2 += decompressBinary(string2[i]);
            }
            message = message2;


            //Split encrypted message into its groups of 5 letters(135 numbers)
            List<String> fiveletterwords = new ArrayList<String>();
            int index = 0;
            while (index < message.length()) {
                fiveletterwords.add(message.substring(index, Math.min(index + 135, message.length())));
                index += 135;
            }

            //Loop through and Decrypt each item in the array of fiveletterwords
            String decryptedBinary = "";
            int count = 0;
            while(count < fiveletterwords.size()){
                if(!decryptBinary(fiveletterwords.get(count)).equals(null)){
                    decryptedBinary = decryptedBinary + decryptBinary(fiveletterwords.get(count));
                    count++;
                } else {
                    Toast.makeText(MainActivity.this, "Key didn't match the encrypted message", Toast.LENGTH_LONG).show();
                    break;
                }

            }

            //Split each item in the fiveletterwords array into groups of 27 and add to array so
            //We can convert them into their letter form
            int index2 = 0;
            List<String> newPos = new ArrayList<String>();
            while (index2 < decryptedBinary.length()) {
                newPos.add(decryptedBinary.substring(index2, Math.min(index2 + 27, decryptedBinary.length())));
                index2 += 27;
            }

            sb = new StringBuilder();
            for(i = 0; i < newPos.size(); i++){
                if(!convertBinary(newPos.get(i)).equals(null)){
                    convertedBinary += convertBinary(newPos.get(i));
                    encryptedMessage_ET.setText("");
                    plainText_ET.setText(convertedBinary);
                    if(plainText_ET.getText().toString().equals("")){
                        Toast.makeText(MainActivity.this, "Key didn't match the encrypted message", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Key didn't match the encrypted message", Toast.LENGTH_LONG).show();
                    break;
                }

            }
        }
    }

    /** Check to see if inputed String is a letter **/
    public static boolean isLetter(String s){
        for( int i=0; i<s.length(); i++){
            char ch = s.charAt(i);
            if (Character.isLetter(ch) || ch == ' ') {
                continue;
            }
            return false;
        }
        return true;
    }

    /** Converts the String letter into its encoded form **/
    public String convertText(String letter){
        String encryptedLetter = "";

        /** PROPRIETARY INFORMATION **/

        return encryptedLetter;
    }

    /** Converts the binary groups of 5 into its compressed letter form **/
    public String compressBinary(String binary){
        String compressedBinary = "";

        if(binary.equals("00000")){
            compressedBinary = "A";
        } else if(binary.equals("00001")){
            compressedBinary = "B";
        } else if(binary.equals("00010")){
            compressedBinary = "C";
        } else if(binary.equals("00011")){
            compressedBinary = "D";
        } else if(binary.equals("00100")){
            compressedBinary = "E";
        } else if(binary.equals("00101")){
            compressedBinary = "F";
        } else if(binary.equals("00110")){
            compressedBinary = "G";
        } else if(binary.equals("00111")){
            compressedBinary = "H";
        } else if(binary.equals("01000")){
            compressedBinary = "I";
        } else if(binary.equals("01001")){
            compressedBinary = "J";
        } else if(binary.equals("01010")){
            compressedBinary = "K";
        } else if(binary.equals("01011")){
            compressedBinary = "L";
        } else if(binary.equals("01100")){
            compressedBinary = "M";
        } else if(binary.equals("01101")){
            compressedBinary = "N";
        } else if(binary.equals("01110")){
            compressedBinary = "O";
        } else if(binary.equals("01111")){
            compressedBinary = "P";
        } else if(binary.equals("10000")){
            compressedBinary = "Q";
        } else if(binary.equals("10001")){
            compressedBinary = "R";
        } else if(binary.equals("10010")){
            compressedBinary = "S";
        } else if(binary.equals("10011")){
            compressedBinary = "T";
        } else if(binary.equals("10100")){
            compressedBinary = "U";
        } else if(binary.equals("10101")){
            compressedBinary = "V";
        } else if(binary.equals("10110")){
            compressedBinary = "W";
        } else if(binary.equals("10111")){
            compressedBinary = "X";
        } else if(binary.equals("11000")){
            compressedBinary = "Y";
        } else if(binary.equals("11001")){
            compressedBinary = "Z";
        } else if(binary.equals("11010")){
            compressedBinary = "2";
        } else if(binary.equals("11011")){
            compressedBinary = "3";
        } else if(binary.equals("11100")){
            compressedBinary = "4";
        } else if(binary.equals("11101")){
            compressedBinary = "5";
        } else if(binary.equals("11110")){
            compressedBinary = "6";
        } else if(binary.equals("11111")){
            compressedBinary = "7";
        }

        return compressedBinary;
    }

    /** Converts the binary compressed letter form into its group of 5 number form **/
    public String decompressBinary(String compressedBinary){
        String decompressedBinary = "";

        if(compressedBinary.equals("A")){
            decompressedBinary = "00000";
        } else if(compressedBinary.equals("B")){
            decompressedBinary = "00001";
        } else if(compressedBinary.equals("C")){
            decompressedBinary = "00010";
        } else if(compressedBinary.equals("D")){
            decompressedBinary = "00011";
        } else if(compressedBinary.equals("E")){
            decompressedBinary = "00100";
        } else if(compressedBinary.equals("F")){
            decompressedBinary = "00101";
        } else if(compressedBinary.equals("G")){
            decompressedBinary = "00110";
        } else if(compressedBinary.equals("H")){
            decompressedBinary = "00111";
        } else if(compressedBinary.equals("I")){
            decompressedBinary = "01000";
        } else if(compressedBinary.equals("J")){
            decompressedBinary = "01001";
        } else if(compressedBinary.equals("K")){
            decompressedBinary = "01010";
        } else if(compressedBinary.equals("L")){
            decompressedBinary = "01011";
        } else if(compressedBinary.equals("M")){
            decompressedBinary = "01100";
        } else if(compressedBinary.equals("N")){
            decompressedBinary = "01101";
        } else if(compressedBinary.equals("O")){
            decompressedBinary = "01110";
        } else if(compressedBinary.equals("P")){
            decompressedBinary = "01111";
        } else if(compressedBinary.equals("Q")){
            decompressedBinary = "10000";
        } else if(compressedBinary.equals("R")){
            decompressedBinary = "10001";
        } else if(compressedBinary.equals("S")){
            decompressedBinary = "10010";
        } else if(compressedBinary.equals("T")){
            decompressedBinary = "10011";
        } else if(compressedBinary.equals("U")){
            decompressedBinary = "10100";
        } else if(compressedBinary.equals("V")){
            decompressedBinary = "10101";
        } else if(compressedBinary.equals("W")){
            decompressedBinary = "10110";
        } else if(compressedBinary.equals("X")){
            decompressedBinary = "10111";
        } else if(compressedBinary.equals("Y")){
            decompressedBinary = "11000";
        } else if(compressedBinary.equals("Z")){
            decompressedBinary = "11001";
        } else if(compressedBinary.equals("2")){
            decompressedBinary = "11010";
        } else if(compressedBinary.equals("3")){
            decompressedBinary = "11011";
        } else if(compressedBinary.equals("4")){
            decompressedBinary = "11100";
        } else if(compressedBinary.equals("5")){
            decompressedBinary = "11101";
        } else if(compressedBinary.equals("6")){
            decompressedBinary = "11110";
        } else if(compressedBinary.equals("7")){
            decompressedBinary = "11111";
        }

        return decompressedBinary;
    }

    /** Encrypts the binary message from the inputed key**/
    public String encryptBinary(String binaryMessage){
        String encryptedBinary = "";
        String returnThisString = "";

        //Get key from key input field
        key = keyInput_ET.getText().toString();

        //Split the key from the keyName_ET and the numbers
        String[] removeKeyName;
        removeKeyName = key.split("! ");
        if(removeKeyName.length == 2){
            key = removeKeyName[1];
        } else {
            key = keyInput_ET.getText().toString();
        }

        //Check if key needs formatting and
        //Format key i.e remove all commas and spaces
        result = key.split(", ");
        if(result.length > 0 ){
            key = "";
            for (String aResult : result) {
                key += aResult;
            }
        } else {
            key = keyInput_ET.getText().toString();
        }

        if( key.length() == 405){

            //Split key string into groups of 3 and add to array
            newPositions = new ArrayList<String>();
            int index = 0;
            while (index < key.length()) {
                newPositions.add(key.substring(index, Math.min(index + 3, key.length())));
                index += 3;

            }

            //Turn the inputed Binary Message into an array of individual numbers
            String[] result2 = binaryMessage.split("");

            //Initialize string array and specify it to contain 135 items
            String[] encryptedBinaryArray = new String[135];

            //encryptButton the result array(inputed binary)
            for(i = 0; i < encryptedBinaryArray.length; i++){

                try{
                    if(encryptedBinaryArray.length == 135){
                        //Check if number is an integer(in case someone entered a letter in the key area)
                        if (Integer.parseInt(newPositions.get(i)) == (int)Integer.parseInt(newPositions.get(i))){
                            int spot = Integer.parseInt(newPositions.get(i));
                            encryptedBinaryArray[i] = result2[spot + 1] ;
                        } else {
                            Toast.makeText(this, "invalid key", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else {
                        Toast.makeText(this, "invalid key", Toast.LENGTH_LONG).show();
                        break;
                    }
                } catch(Exception e){
                    Toast.makeText(MainActivity.this, "Invalid Key", Toast.LENGTH_LONG).show();
                    System.out.println("not number");
                    break;
                }

            }

            //Loop through each item in array and create a new string through a stringbuilder
            StringBuilder mySB = new StringBuilder();
            int d = 0;
            for (String anEncryptedBinaryArray : encryptedBinaryArray) {
                //mySB.append(encryptedBinaryArray[d]);
                encryptedBinary += anEncryptedBinaryArray;
                d++;
            }

            //Split encryptedBinary into groups of 5 numbers
            List<String> newPositions3 = new ArrayList<String>();
            int index2 = 0;
            while (index2 < encryptedBinary.length()) {
                newPositions3.add(encryptedBinary.substring(index2, Math.min(index2 + 5, encryptedBinary.length())));
                index2 += 5;
            }

            //Rebuild encryptedBinary string with a "," between every 5 characters
            for(i = 0; i < newPositions3.size(); i++){
                if(i < newPositions3.size()){

                    returnThisString += compressBinary(newPositions3.get(i));
                    //returnThisString += (", " + compressBinary(newPositions3.get(i)));
                    //Log.d("CHECK NUMBER", newPositions3.get(i));


                }
            }

            //return encrypted message;
            return returnThisString;
        } else {
            Toast.makeText(MainActivity.this, "invalid key", Toast.LENGTH_SHORT).show();
            return " ";
        }

        }

    /** Finally splits up the final encrypted message into groups of 5 for easy translation **/
    public String splitEncryptedAndCompressedBinary(String string){
        String splitString = "";

        //Split compressedBinary into groups of 5 letters
        List<String> newPositions4 = new ArrayList<String>();
        int index3 = 0;
        while (index3 < string.length()) {
            newPositions4.add(string.substring(index3, Math.min(index3 + 5, string.length())));
            index3 += 5;
        }

        //Rebuild returnThisString string with a "," between every 5 letters
        for(i = 0; i < newPositions4.size(); i++) {
            if (i < newPositions4.size()) {

                if (newPositions4.get(i).length() == 4) {
                    String nString = newPositions4.get(i) + "2";
                    splitString += (", " + nString);
                } else if (newPositions4.get(i).length() == 3) {
                    String nString = newPositions4.get(i) + "22";
                    splitString += (", " + nString);
                } else if (newPositions4.get(i).length() == 2) {
                    String nString = newPositions4.get(i) + "222";
                    splitString += (", " + nString);
                } else if (newPositions4.get(i).length() == 1) {
                    String nString = newPositions4.get(i) + "2222";
                    splitString += (", " + nString);
                } else {
                    splitString += (", " + newPositions4.get(i));
                }
            }
        }
        return splitString;
    }

    /** Decrypts the binary message from the inputed key**/
    public String decryptBinary(String encryptedMessage){
        String decryptedBinary = "";

        //Get key from key input field
        String key = keyInput_ET.getText().toString();

        //Split the key from the keyName_ET and the numbers
        String[] removeKeyName;
        removeKeyName = key.split("! ");
        if(removeKeyName.length == 2){
            key = removeKeyName[1];
        } else {
            key = keyInput_ET.getText().toString();
        }

        result = key.split(", ");
        key = "";
        for (String aResult : result) {
            key += aResult;
        }

        //Split key string into groups of 3 and add to array
        newPositions = new ArrayList<String>();
        int index = 0;
        while (index < key.length()) {
            newPositions.add(key.substring(index, Math.min(index + 3, key.length())));
            index += 3;
        }

        //Turn the inputed Encrypted Message into an array of individual numbers
        String[] result = encryptedMessage.split("");

        //Initialize string array and specify it to contain 135 items
        String[] decryptedBinaryArray = new String[135];

        //decryptButton the result array(inputed binary)
        co = 0;

            if(result.length - 1 != decryptedBinaryArray.length){
                //Toast.makeText(MainActivity.this, "Key invalid or message is formed improperly", Toast.LENGTH_SHORT).show();
                co = decryptedBinaryArray.length;
            } else {
                while(co < decryptedBinaryArray.length) {
                    try {
                        if(decryptedBinaryArray.length == 135){
                            //Check if number is an integer(in case someone entered a letter in the key area)
                            if ((int)Integer.parseInt(newPositions.get(co)) >= 0){
                                int spot = Integer.parseInt(newPositions.get(co));
                                decryptedBinaryArray[spot] = result[co + 1];
                                co ++;
                            } else {
                                Toast.makeText(this, "invalid key", Toast.LENGTH_LONG).show();
                                break;
                            }
                        } else {
                            Toast.makeText(this, "invalid key", Toast.LENGTH_LONG).show();
                            break;
                        }
                    } catch (Exception e){
                        Toast.makeText(MainActivity.this, "Invalid Key", Toast.LENGTH_LONG).show();
                        System.out.println("not number");
                        break;
                    }

                }
            }


        //Loop through each item in array and create a new string through a stringbuilder
        StringBuilder mySB = new StringBuilder();
        int d = 0;
        for (String anEncryptedBinaryArray : decryptedBinaryArray) {
            //mySB.append(encryptedBinaryArray[d]);
            decryptedBinary += anEncryptedBinaryArray;
            d++;
        }




        return decryptedBinary;
    }

    /** Decodes number back to its letter form **/
    public String convertBinary(String letter){
        String convertedBinary = "";

        /** PROPRIETARY INFORMATION **/

        return convertedBinary;
    }


    /** Copies the encrypted message to the phone clipboard **/
    private void copyEncryptedMessageToClipboard() {
        ClipData clip = ClipData.newPlainText("Encrypted Message", "***Curb Encrypted***" + encryptedMessage_ET.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this, "Encrypted message successfully copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    /** Copies the current key to the phone clipboard **/
    private void copyKeyToClipboard() {
        ClipData clip;
        if(!keyName_ET.getText().toString().equals("")){

            //Split the key from the keyName_ET and the numbers
            String[] splitKeyFromName;
            splitKeyFromName = keyInput_ET.getText().toString().split("! ");
            if(splitKeyFromName.length == 2){
                key = splitKeyFromName[1];
                clip = ClipData.newPlainText("Key", "!**" + keyName_ET.getText().toString() + "**! " + key );
                keyName_ET.setText("");
            } else {
                clip = ClipData.newPlainText("Key", "!**" + keyName_ET.getText().toString() + "**! " + keyInput_ET.getText().toString());
                keyName_ET.setText("");
            }
        } else {
            clip = ClipData.newPlainText("Key", keyInput_ET.getText().toString());
            keyName_ET.setText("");
        }
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this, "Key successfully copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    /** Generate a new key **/
    public String generateKey() {
        String returnThisString = "";
        keyInput_ET.setText("");
        String key = "/** PROPRIETARY INFORMATION **/";

        //Get rid of all commas
        String temp = key.replace(",", "");
        //Get rid of all spaces
        String formattedKey = temp.replaceAll("\\s+", "");

        //Split key string into groups of 3 and add to array
        newPositions = new ArrayList<String>();
        int index = 0;
        while (index < formattedKey.length()) {
            newPositions.add(formattedKey.substring(index, Math.min(index + 3, formattedKey.length())));
            index += 3;

        }

        tempArray = new String[135];
        for (i = 0; i < newPositions.size(); i++) {
            tempArray[i] = newPositions.get(i);
        }

        Collections.shuffle(Arrays.asList(tempArray));
        for (i = 0; i < tempArray.length; i++) {
            keyString += tempArray[i];

        }


        //Split key into groups of 5 numbers
        List<String> newPositions3 = new ArrayList<String>();
        int index2 = 0;
        while (index2 < keyString.length()) {
            newPositions3.add(keyString.substring(index2, Math.min(index2 + 5, keyString.length())));
            index2 += 5;
        }

        //Rebuild encryptedBinary string with a "," between every 5 characters

        for(i = 0; i < newPositions3.size(); i++){
            if(i < newPositions3.size()){
                returnThisString += (", " + newPositions3.get(i));
            }
        }

        //Check if they entered a name for the key
        if(!keyName_ET.getText().toString().equals("")){
            //If key name is entered, set it before the generated key
            keyInput_ET.setText("!**" + keyName_ET.getText().toString() + "**! " + returnThisString.substring(2));
        } else {
            keyInput_ET.setText(returnThisString.substring(2));
        }

        return keyString;
    }

}
