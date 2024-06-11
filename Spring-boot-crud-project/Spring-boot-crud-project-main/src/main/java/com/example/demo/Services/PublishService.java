package com.example.demo.Services;

import com.example.demo.Controller.PublishAlreadyExistsException;
import com.example.demo.DTOs.Publishview;
import com.example.demo.Model.Publish;

import java.util.List;

public interface PublishService {

    List<Publish> getAllPublish();
    void savePublish(Publish publish)throws PublishAlreadyExistsException;

    Publish createPublish(Publishview publishview)throws PublishAlreadyExistsException;
    Publish getPublishById(Long id) throws PublishNotFoundException;





    void updatePublish(Publish publish) throws PublishNotFoundException;

    void deletePublish(Long id) throws PublishNotFoundException;
}

