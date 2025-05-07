package com.springboot.blogApp.Controller;

import DTO_payLoad.PostDto;
import DTO_payLoad.PostDtoV2;
import DTO_payLoad.PostResponse;
import com.springboot.blogApp.Repository.CategoryRepository;
import com.springboot.blogApp.Service.PostService;
import com.springboot.blogApp.Utils.AppConstants;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private PostService postService;


    public PostController(PostService postService, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postService = postService;

    }



    //Create blog post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
    return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/api/posts")
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value= "sortDir", defaultValue= AppConstants.DEFAULT_SORT_DIRECTION, required=false) String sortDirection
    ){
        return postService.getAllPosts(pageNo, pageSize,sortBy, sortDirection);
    }

//    @GetMapping("/api/v1/posts/{id}")
//    @GetMapping(value = "/api/posts/{id}",params = "version=1") //version through query parameter
//@GetMapping(value = "/api/posts/{id}",headers = "X-API-version=1")//version through headers
//@GetMapping(value = "/api/posts/{id}",produces = "application/vnd.musab/v2+json")//version through content negotiation
//@GetMapping(value = "/api/posts/{id}")
//    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") Long id){
//        return ResponseEntity.ok(postService.getPostById(id));
//    }
    @GetMapping(value="/api/posts/{id}")
    public ResponseEntity<PostDtoV2> getPostByIdV2(@PathVariable(name = "id") Long id){
        PostDto postDto=postService.getPostById(id);
        PostDtoV2 postDtoV2=new PostDtoV2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setDescription(postDto.getDescription());
        List<String> tags=new ArrayList<>();
        tags.add("java");
        tags.add("python");
        tags.add("php");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable( name = "id") Long id){
        PostDto postResponse=postService.updatePost(postDto,id);
        return ResponseEntity.ok(postResponse);
//        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Deleted post with id: "+id);
    }
    //Build Get Post by Category REST API
    @GetMapping("/api/posts/category/{id}")
    public ResponseEntity<PostDto> getPostsByCategory(Long categoryId){
        List<PostDto> postDtos=postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postDtos.get(0));
    }
}

