import { Link } from 'react-router-dom';
import classes from './PostItemCard.module.css'

const PostItemCard = ({post}) => {
    
    return(
        <>
            <li className={classes["post-li"]}>
                <Link to ={`/post/${post.uid}`}>
                <div className={classes["post-card"]}>
                    <h3 className={classes["post-card-title"]}>{post.title}</h3>
                    <div className={classes["post-card-content"]}>
                        {post.content}
                    </div>
                    <div className={classes["post-card-bottom"]}>

                        <div className={classes["post-card-path"]}>
                            <span>경로</span>
                        </div>

                        <div className={classes["post-card-btn"]}>
                            <span className={classes["edit-btn"]}>수정</span>
                            <span className={classes["delete-btn"]}>삭제</span>
                        </div>
                    </div>

                </div>
                </Link>
            </li>
        </>
    )
}

export default PostItemCard;