package groovey.didactic.disco.org.didacticdisco.models;


import com.google.gson.annotations.SerializedName;

public class Line {

    @SerializedName("fields")
    Fields fields;

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }
}
