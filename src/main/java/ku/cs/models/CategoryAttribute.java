package ku.cs.models;

import javafx.scene.image.Image;

import java.util.List;

public class CategoryAttribute {
    private String idCategory;
    private String nameAttribute;
    private String typeAttribute;
    private List<String> inputData;
    private List<String> answerData;
    private List<Image> image;

    public CategoryAttribute(String idCategory, String nameAttribute,String typeAttribute,List<String> inputData, List<String> answerData, List<Image> image) {
        this.idCategory = idCategory;
        this.nameAttribute = nameAttribute;
        this.typeAttribute = typeAttribute;
        this.inputData = inputData;
        this.answerData = answerData;
        this.image = image;
    }



    public String getIdCategory() {
        return idCategory;
    }

    public String getNameAttribute() {
        return nameAttribute;
    }

    public List<String> getInputData() {
        return inputData;
    }

    public String getTypeAttribute() {
        return typeAttribute;
    }

    public List<String> getAnswerData() {
        return answerData;
    }

    public List<Image> getImage() {
        return image;
    }

    @Override
    public String toString() {
        return nameAttribute;
    }
}
