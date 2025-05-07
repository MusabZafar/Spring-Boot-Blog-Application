package com.springboot.blogApp.Service.IMPL;

import DTO_payLoad.PostDto;
import DTO_payLoad.PostResponse;
import com.springboot.blogApp.Entity.Category;
import com.springboot.blogApp.Entity.Post;
import com.springboot.blogApp.Exception.ResourceNotFound;
import com.springboot.blogApp.Repository.CategoryRepository;
import com.springboot.blogApp.Repository.PostRepository;
import com.springboot.blogApp.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    /*
    We use PostRepository in the PostServiceImpl class to interact with the database and perform CRUD
    (Create, Read, Update, Delete) operations on Post entities. Here’s a detailed explanation of why and how PostRepository is used:
     */

    private PostRepository postRepository;
    private ModelMapper mapper;


    /*
    The Spring framework uses its IoC container to manage the lifecycle and configuration of application objects.
    By annotating the constructor with @Autowired, Spring automatically injects the required PostRepository bean at runtime.
     */

    // The constructor takes a PostRepository object as a parameter.
//    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Convert DTO to entity
//        Post post=new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFound("Category", "id", postDto.getCategoryId()));

        // convert DTO to entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = maptoDto(newPost);
        return postResponse;
//        postResponse.setId(newPost.getId());
//        postResponse.setTitle(newPost.getTitle());
//        postResponse.setContent(newPost.getContent());
//        postResponse.setDescription(newPost.getDescription());

    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {

        Sort sort =sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        //create Pageable instance

//        Pageable pageable= PageRequest.of(pageNo,pageSize);
//        Pageable pageable=PageRequest.of(pageNo,pageSize);
            Pageable pageable=PageRequest.of(pageNo,pageSize, Sort.by(sortBy));
        Page<Post> posts=postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts=posts.getContent();
//        List<Post> ListOfposts=posts.getContent();
//        List<Post> posts=postRepository.findAll();
        List<PostDto> content= listOfPosts.stream().map(post -> maptoDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLastPage(posts.isLast());
        return postResponse;

    }

    @Override
    public PostDto getPostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","id", id));
        return maptoDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Post", "id", id));

        Category categoryst = categoryRepository.findById(poDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFound("Category", "id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(postDto.getCategoryId());
        Post updatedPost = postRepository.save(post);
        return maptoDto(updatedPost);

    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFound("Category", "id", categoryId));
        List<Post> posts=postRepository.findCategoryById(categoryId);
        return posts.stream().map((post) -> maptoDto(post)).collect(Collectors.toList());
    }


    @Override
    public void deletePostById(Long id) {
        Post post=postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","id", id));
        postRepository.delete(post);
    }

    //Convert DTO to entity
    private Post mapToEntity(PostDto postDto) {
     Post post=mapper.map(postDto, Post.class);

//        Post post  = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }

    //Convert Entity to DTO
    private PostDto maptoDto(Post post) {
        PostDto postDto=mapper.map(post,PostDto.class);
//        PostDto postDto=new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setContent(post.getContent());
//        postDto.setDescription(post.getDescription());
        return postDto;
    }
}
