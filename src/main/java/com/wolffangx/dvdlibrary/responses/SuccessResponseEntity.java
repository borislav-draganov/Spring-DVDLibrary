package com.wolffangx.dvdlibrary.responses;

import com.wolffangx.dvdlibrary.entities.*;
import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.LinkedList;
import java.util.List;

/**
 * A standard response format that's going to be send by the server on successful operations
 *
 * @author borislav.draganov
 */

// @XmlRootElement - makes the class serializable in XML and JSON. Also sets the name of the XML root element as "response"
// @XmlSeeAlso - allows generics to be used with the supplied class. If a class is not included a runtime exception will be thrown during serialization
@XmlRootElement(name = "response")
@XmlSeeAlso({Audio.class, DVD.class, Genre.class, Language.class, Movie.class, Rating.class})
public class SuccessResponseEntity<T> extends AbstractResponseEntity {
    private List<T> data;

    public SuccessResponseEntity() {
        success = true;
    }

    public SuccessResponseEntity(HttpStatus httpStatus, List<T> data) {
        success = true;
        this.statusCode = httpStatus.value();
        this.statusMessage = httpStatus.getReasonPhrase();

        this.data = data;
    }

    public SuccessResponseEntity(HttpStatus httpStatus, T data) {
        success = true;
        this.statusCode = httpStatus.value();
        this.statusMessage = httpStatus.getReasonPhrase();

        List<T> list = new LinkedList<T>();
        list.add(data);
        this.data = list;
    }

    // Getters and Setters
    // XmLElementWrapper generates a wrapper element around XML representation
    @XmlElementWrapper(name = "data")
    // XmlElement sets the name of the entities
    @XmlElement(name = "entry")
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}