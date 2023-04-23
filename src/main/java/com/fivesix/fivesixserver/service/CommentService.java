package com.fivesix.fivesixserver.service;

import com.fivesix.fivesixserver.classifier.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

@Service
@Slf4j
public class CommentService {
    public double getScore(String text) throws IOException, ClassNotFoundException {
        String path = System.getProperty("user.dir")+"/src/main/java/com/fivesix/fivesixserver/service/weibo-model";
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        Object o = ois.readObject();
        Model model = (Model) o;
        return model.nb(text);
    }

}
