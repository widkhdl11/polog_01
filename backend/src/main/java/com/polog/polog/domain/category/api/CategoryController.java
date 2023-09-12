package com.polog.polog.domain.category.api;


import com.polog.polog.domain.category.application.CategoryService;
import com.polog.polog.domain.category.domain.Category;
import com.polog.polog.domain.category.dto.*;
import com.polog.polog.domain.member.api.MemberController;
import com.polog.polog.domain.member.domain.Member;
import com.polog.polog.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final MemberController memberController;

    /**
     * 카테고리 생성
     * @param request
     * @return
     */
    @PostMapping("/api/category/create/{memberId}")
    public void createCategory(@PathVariable("memberId") String memberId, @RequestBody CreateCategoryRequest request){

        System.out.println("카테고리 생성 실행");

        Member findMember = memberController.findOneMemberId(memberId);
        MemberDto memberDto = Member.toDto(findMember);
        request.setMember(memberDto);

        Long createUid = categoryService.createCategory(CreateCategoryRequest.toEntity(request));

//        List<Category> categoryList = categoryService.findAllCategory(memberId);
//
//
//
//        System.out.println("카테고리 생성 종료");
//
//        List<CreateCategoryResponse> collect = categoryList.stream()
//                .map(c -> new CreateCategoryResponse(
//                        c.getUid(),
//                        c.getName(),
//                        c.getParentUid(),
//                        c.getOrder(),
//                        c.getStep()
//                ))
//                .collect(Collectors.toList());
    }

    /**
     * 카테고리 목록
     * @return
     */
    @GetMapping("/api/category/{memberId}")
    public List<TreeNode> findAllCategory(@PathVariable("memberId") String memberId){
        System.out.println(" 카테고리 불러오기 실행");

        // category 리스트 불러오기
        List<Category> categoryList = categoryService.findAllCategory(memberId);

        //parentUid 추출
        Set<Long> nonParentUidSet = getUniqueParentUids(categoryList).stream().sorted().collect(Collectors.toSet());


        ParentUidMap parentUidMap = new ParentUidMap();
        for(Long parentUid : nonParentUidSet){

            List<CategoryDto> targetCategoryList = categoryList.stream()
                    .filter(c -> c.getParentUid() == parentUid)
                    .map(c -> Category.toDto(c))
                    .collect(Collectors.toList());

            parentUidMap.setParentUidMap(parentUid,targetCategoryList);

        }

        List<TreeNode> result_list = new ArrayList<>();


        for (Map.Entry<Long, List<CategoryDto>> entry : parentUidMap.getParentUidMap().entrySet()) {

            Long parentUid = entry.getKey();
            List<CategoryDto> targetCategoryList = entry.getValue();

            // 자식 노드들 생성
            for (CategoryDto category : targetCategoryList) {
                TreeNode rootNode = new TreeNode(category);

                if (parentUid != 0L) {
                    for(TreeNode node : result_list){
                        TreeNode parentNode = node.searchParentTreeNode(rootNode);
                        if (parentNode != null){
                            parentNode.addChildNode(rootNode); //parentNode의 자식노드로 category 등록
                            break;
                        }
                    }
                }
                if(parentUid==0L){
                    result_list.add(rootNode);
                }

            }
        }

        return result_list;
    }

    /**
     * 카테고리 수정
     * @param request
     */
    @PatchMapping("/api/category/update/{memberId}")
    public void updateCategory(@PathVariable("memberId") String memberId, @RequestBody UpdateCategoryRequest request){
        System.out.println("카테고리 업데이트 실행");

        categoryService.updateCategory(UpdateCategoryRequest.toEntity(request));
        System.out.println(request.toString());
        System.out.println("카테고리 업데이트 종료");

    }

    /**
     * 카테고리 삭제
     * @param request
     */
    @DeleteMapping("/api/category/delete/{memberId}")
    public void deleteCategory(@PathVariable("memberId") String memberId, @RequestBody DeleteCategoryRequest request){

        System.out.println("카테고리 삭제");
        Member findMember = memberController.findOneMemberId(memberId);
        MemberDto memberDto = Member.toDto(findMember);

        request.setMember(memberDto);
        System.out.println(request.getUid());
        System.out.println(request.getMember().getUid());

        categoryService.deleteCategory(DeleteCategoryRequest.toEntity(request));

    }


    // 추출해서 생성한 리스트 정렬 매서드
    public List<Category> sortCategory(List<Category> list){
        List<Category> result = new ArrayList<>();

        List<Category> copy_list = new ArrayList<>();
        for(Category c : list){
            copy_list.add(c);
        }


        int total_count = list.size();

        for(int j = 0 ; j < total_count; j++){

            int min_order = copy_list.get(0).getOrder();
            int index = 0;

            for(int i =0 ; i<copy_list.size() ; i++){

                if(copy_list.get(i).getOrder() < min_order ){
                    min_order = copy_list.get(i).getOrder();
                    index = i;
                }
            }

            result.add(copy_list.get(index));
            copy_list.remove(index);

        }
        return result;
    }

    // step 추출 메서드
    public Set<Integer> getUniqueSteps(List<Category> categoryList) {
                Set<Integer> uniqueSteps = categoryList.stream()
                        .map(Category::getStep)
                        .collect(Collectors.toSet());
        return uniqueSteps;
    }

    // order 추출 메서드
    public Set<Integer> getUniqueOrders(List<Category> categoryList) {
        Set<Integer> uniqueOrders = categoryList.stream()
                .map(Category::getOrder)
                .collect(Collectors.toSet());
        return uniqueOrders;
    }

    // parentUid 추출 메서드
    public Set<Long> getUniqueParentUids(List<Category> categoryList) {
        Set<Long> uniqueParentUid = categoryList.stream()
                .map(Category::getParentUid)
                .collect(Collectors.toSet());
        return uniqueParentUid;
    }


}
