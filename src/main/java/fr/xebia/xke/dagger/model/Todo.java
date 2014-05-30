package fr.xebia.xke.dagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;
import org.jongo.marshall.jackson.oid.ObjectId;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {


    private final String _id;

    private final String title;

    private final String description;

    @JsonCreator
    public Todo(@ObjectId @Id String _id, @JsonProperty("title") String title, @JsonProperty("description") String description) {
        this._id = _id;
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
