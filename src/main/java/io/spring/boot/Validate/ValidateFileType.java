package io.spring.boot.Validate;

import java.io.File;

public class ValidateFileType {

//   private List<String> file_extension = new ArrayList<>();

    public ValidateFileType() {
    }

    public int IsValidImage(String file_path){

            File fileToCheck = new File(file_path);

//            if(!fileToCheck.exists()){
//              return 0;
//                return "hi "+fileToCheck;
//            }
            String fileName = fileToCheck.getName();

            String extension ="";

            int i = fileName.lastIndexOf(".");
            if(i>=0){
                extension = fileName.substring(i+1);
            }
            String [] extensionImg = {"png","jpg","jpeg","pdf"};

        for (String item: extensionImg ) {
            if(extension.equals(item)){

                return 1;
            }
        }

        return 0;
    }
}
