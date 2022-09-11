package ku.cs.models;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CategoryAttribute {
    private String attributeId;
    private String categoryId;
    private String nameAttribute;
    private String typeAttribute;
    private List<String> inputData;
    private List<String> answerData;
    private List<Image> image;

    public CategoryAttribute(String attributeId, String categoryId, String nameAttribute,String typeAttribute,List<String> inputData, List<String> answerData, List<Image> image) {
        this.attributeId = attributeId;
        this.categoryId = categoryId;
        this.nameAttribute = nameAttribute;
        this.typeAttribute = typeAttribute;
        this.inputData = inputData;
        this.answerData = answerData;
        this.image = image;
    }

    public CategoryAttribute(String categoryId, String nameAttribute, String typeAttribute) {
        this(UUID.randomUUID().toString(), categoryId, nameAttribute, typeAttribute, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public void addInputData(String choice) {
        inputData.add(choice);
    }

    public void changeChoiceName(String oldChoiceName, String newChoiceName) {
        inputData.set(inputData.indexOf(oldChoiceName), newChoiceName);
    }

    public void removeChoice(String choiceName) {
        inputData.remove(choiceName);
    }

    public String getAttributeId() {
        return attributeId;
    }
    public String getIdCategory() {
        return categoryId;
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

    public void setNameAttribute(String nameAttribute) {
        this.nameAttribute = nameAttribute;
    }

    public String[] toStringArray() {
        return new String[] {
                attributeId,
                categoryId,
                nameAttribute,
                typeAttribute,
                String.join(",", inputData),
                String.join(",",  answerData),
        };
    }
    @Override
    public String toString() {
        return nameAttribute;
    }
}
