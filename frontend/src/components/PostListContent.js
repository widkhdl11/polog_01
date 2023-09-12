import { Link } from 'react-router-dom';
import PostItem from './PostItem';
import PostItemCard from './PostItemCard';
import classes from './PostListContent.module.css'

const PostListContent = ({title, posts}) => {
    
    return(
        <>
        <div>
            <h1>{title}</h1>

            <ul className={classes.list}>
                { posts ? posts.map( (post)  => (
                    <PostItemCard key={post.uid} post={post}/>

                ))
            :"글없음"
            }
            </ul>
        </div>

        </>
    )
}

export default PostListContent;