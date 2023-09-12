import { Form, json, redirect } from 'react-router-dom';
import classes from './PostForm.module.css';
import axios from 'axios';

const PostForm = ( { method,post } ) => {

    return(
        <>
        <Form method={method} className={classes.form}>
            <p>글 생성</p>
            <label htmlFor="title">제목</label>
            <input
                id="title"
                type="text"
                name="title"
                required
                defaultValue={post ? post.title : ''}
            />
            <label htmlFor="content">내용</label>
            <input
                id="content"
                type="text"
                name="content"
                required
                defaultValue={post ? post.content : ''}
            />
            <button>
                { post ? "수정완료" : "저장" }
            </button>
        </Form>
        </>
    )
}
export default PostForm;

export const action = async ({request, params}) =>{

    const data = await request.formData();
    let resUid = 0;

    const method= request.method;

    let formData = {
        title : data.get('title'),
        content : data.get("content")
    }
    console.log("title : ",data.get('title'))
    console.log("content : ",data.get('content'))

    let url ="/api/post";

    if (method === "PATCH"){
        
        url += "/update"

        formData.uid = params.postUid
        
        formData.category = {
            uid : 1,
        }
        formData.member = {
            uid : 1,
        }

    }   
    else{
        url += "/new"
    }

    console.log("url : ",url)
    console.log("method : ",method)
    console.log("formData : ",formData)

    try{

        const response = await axios(url,{

            method: method,
            headers : {'Content-Type' : 'application/json'},
            data: formData
            
        })

        console.log(response)
    
        if (response.status === 422) {
            return response.data;
        }
        
        if (!response.statusText === "OK") {
            throw json({ message: 'Could not save event.' }, { status: 500 });
        }
    
        resUid = response.data.uid;


    }catch( error ){
        
        console.log(error);
    };



    return redirect(`/post/${resUid}`);
}