package com.polog.polog.domain.category.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreeNode {

    private CategoryDto nodeCategory; // 현재 노드의 카테고리
    @JsonIgnore
    private TreeNode parentNode; // 부모 노드
    private ArrayList<TreeNode> childNodeArray = new ArrayList<>(); // 다중 자식노드를 등록하기 위한 ArrayList

    //생성자
    public TreeNode(CategoryDto nodeCategory) {
        this.nodeCategory = nodeCategory;
        childNodeArray = new ArrayList<TreeNode>();
        parentNode = null;
    }

    /*
     * 부모노드 set 함수
     * parentNode를 매개변수로 전달받으면, 전달받은 parentNode를 현재노드의 부모노드로 set
     */
    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }


    /*
     * 다중자식노드 add 함수
     * nodeName을 매개변수로 전달받으면, 전달받은 nodeName으로 childNode(자식노드) 생성
     * childNode(자식노드)에 현재 노드를 부모노드로 set
     * 현재 노드의 childNodeArray(자식 노드 리스트)에 childNode(자식노드)를 add
     * childNode(자식노드)를 반환
     */
    public TreeNode addChildNode(TreeNode targetNode) {
        TreeNode childNode = new TreeNode(targetNode.getNodeCategory());
        childNode.setParentNode(this);
        childNodeArray.add(childNode);

        return childNode;
    }

    /*
     * 현재 노드 기준으로 하위에 등록되어 있는 모든 자식노드 출력 함수
     * 현재 노드의 정보를 출력하고, 등록되어 있는 자식노드의 출력 함수 실행
     */
    public void searchAllTreeNode() {
        checkNowTreeInfo();

        for(int i=0 ; i<childNodeArray.size() ; i++) {
            TreeNode childNode = childNodeArray.get(i);
            childNode.searchAllTreeNode();
        }
    }

    public TreeNode searchParentTreeNode(TreeNode targetNode) {

        if(this.getNodeCategory().getUid() == targetNode.getNodeCategory().getParentUid()){
            return this;
        }else{

            for(int i=0 ; i<childNodeArray.size() ; i++) {

                TreeNode childNode = childNodeArray.get(i);
                if( childNode.getNodeCategory().getUid() == targetNode.getNodeCategory().getParentUid()){
                    return childNode;
                }
                childNode.searchParentTreeNode(targetNode);
            }
        }

        return null;
    }

    public boolean checkTreeNode(CategoryDto targetCategory) {

        for(int i=0 ; i<childNodeArray.size() ; i++) {
            TreeNode childNode = childNodeArray.get(i);
            if(childNode.nodeCategory.getUid() == targetCategory.getUid()){
                return false;
            }
            childNode.checkTreeNode(childNode.getNodeCategory());
        }

        return true;
    }

    /*
     * 현재 노드의 정보 출력 함수
     */
    public void checkNowTreeInfo() {
        System.out.println("-------------------노드 정보 확인-------------------");

        if(parentNode != null)
            System.out.println("부모 노드 : "+parentNode.getNodeName());
        else
            System.out.println("부모 노드 : 없음");

        System.out.println("현재 노드 : "+nodeCategory.getName());
        System.out.println("자식 노드 갯수 : "+childNodeArray.size());
        System.out.println("자식 노드 명단");

        for(int i=0 ; i<childNodeArray.size(); i++) {
            TreeNode childNode = childNodeArray.get(i);
            System.out.print(i+":"+childNode.getNodeName()+"   ");
        }

        System.out.println("\n-----------------------------------------------\n");
    }

    /*
     * 현재 노드의 이름 get 함수
     */
    public String getNodeName() {
        return nodeCategory.getName();
    }

    /*
     * 현재 노드의 부모 노드 get 함수
     */
    public TreeNode getParentNode() {
        return parentNode;
    }
}
