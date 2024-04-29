package io.spring.boot.Service;


import io.spring.boot.Entity.Category_product;
import io.spring.boot.Entity.Image;
import io.spring.boot.Repository.Category_productRepository;
import io.spring.boot.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> fetchAll(){
        return imageRepository.findAll();
    }

    public Image findById(long id){
        return imageRepository.findById(id).get();
    }

    public void save(Image image){
        imageRepository.save(image);
    }

    public void delete(Long id){
        imageRepository.deleteById(id);
    }




}
