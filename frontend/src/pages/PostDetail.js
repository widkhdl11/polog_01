import { json, redirect, useRouteLoaderData } from "react-router-dom";
import PostItem from "../components/PostItem";
import axios from 'axios';

const PostDetailPage = () => {
    
    const post = useRouteLoaderData('post-detail');

    
    return(
        <>
            <PostItem method="GET" post = {post}></PostItem>
        </>
    )
}

export default PostDetailPage;

export async function loadPost({request, params}) {
    const uid = params.postUid;

    try{
        const response = await axios({
            method : "GET",
            url : '/api/post/'+uid
        })
        if (!response.statusText === "OK") {
            throw json(
            { message: 'Could not fetch details for selected event.' },
            {
                status: 500,
            }
            );
        } else {
            return response.data;
        }
    }catch(error){
        console.log(error);
    }

    return null;
}

export async function action({ params, request }) {
    const postUid = params.postUid;
    const method = request.method

    const url = "/api/post/delete"
    axios({
        url : url+"/"+postUid,
        method : method,
    }).then( function(response){

        if (!response.statusText ==="OK") {
            throw json(
                { message: 'Could not delete event.' },
                {
                    status: 500,
                }
                );
            }
        }
    ).catch( error => {
        console.log("error : ",error)
    })
  
    return redirect('/');
  }
  