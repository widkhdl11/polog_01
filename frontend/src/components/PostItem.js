import { Link, json, useSubmit } from 'react-router-dom';
import classes from './PostItem.module.css'
import axios from 'axios'

const PostItem = ({ post }) => {
    const submit = useSubmit();

    function startDeleteHandler() {
        const proceed = window.confirm('Are you sure?');

        if (proceed) {
            submit(null, { method: 'delete' });
        }
  }

    return(
        <>
            <article className={classes.article}>
                <div className={classes.top}>
                    <h1 className={classes.title}>{post.title}</h1>
                    <time className={classes.date}>작성날짜</time>
                </div>

                <p className={classes.content}>
                    {post.content}
                </p>
                
            </article>
            <menu  className={classes.menu}>
                <button className={classes["edit-btn"]}><Link className={classes.link} to ={`/post/${post.uid}/update`} >수정</Link></button>
                <button className={classes["delete-btn"]} onClick={startDeleteHandler}>삭제</button>
            </menu>
        </>
    )
}

export default PostItem;

// export const action = async ({request, params}) =>{

//     const method = request.method;
//     const data = await request.formData();

//     const eventData = {
//         title: data.get('title'),
//         image: data.get('image'),
//         date: data.get('date'),
//         description: data.get('description'),
//     };
//     let url = 'http://localhost:8080/api/post';

//     if (method === 'GET') {
//       const postUid = params.postUid;
//       url = 'http://localhost:8080/api/post/' + postUid;
//     }

//     const response = await axios(url, {
//         method: method,
//         url : url
//     });

//     if (response.status === 422) {
//         return response;
//     }

//     if (!response.ok) {
//         throw json({ message: 'Could not save event.' }, { status: 500 });
//     }

//     return redirect('/events');
// }