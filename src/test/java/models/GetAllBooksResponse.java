package models;

import java.util.ArrayList;

public class GetAllBooksResponse {

    private ArrayList<Book> value;
    private ArrayList<String> errors;

    public ArrayList<Book> getValue() {
        return value;
    }

//    public void setValue(ArrayList<Book> value) {
//        this.value = value;
//    }

    public ArrayList<String> getErrors() {
        return errors;
    }

//    public void setErrors(ArrayList<String> errors) {
//        this.errors = errors;
//    }


    public GetAllBooksResponse(ArrayList<Book> value, ArrayList<String> errors) {
        this.value = value;
        this.errors = errors;

        ArrayList <Book> bookArrayList = new ArrayList<Book>();
        for (int i=0;i<bookArrayList.size(); i++){
            System.out.println(bookArrayList.get(i));
        }
    }


}
