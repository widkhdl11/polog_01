import PostItem from './PostItem';
import PostItemCard from './PostItemCard';
import classes from './PostListContent.module.css'

const PostListContent = ({title}) =>{
    return(
        <>
        <div>
            <h1>{title}</h1>
            <ul className={classes.list}>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
                <PostItemCard/>
            </ul>
        </div>

        </>
    )
}

export default PostListContent;