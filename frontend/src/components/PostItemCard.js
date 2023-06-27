import { Link } from 'react-router-dom';
import classes from './PostItemCard.module.css'

const PostItemCard = () => {
    const post = {
        id : 1
    }
    return(
        <>
            <li className={classes["post-li"]}>
                <Link to ={`/posts/${post.id}`}>
                <div className={classes["post-card"]}>
                    <h3 className={classes["post-card-title"]}>제목</h3>
                    <div className={classes["post-card-content"]}>
                        내용
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