import { Link } from 'react-router-dom';
import classes from './PostItem.module.css'

const PostItem = () => {
   
    return(
        <>
            <article className={classes.article}>
                <div className={classes.top}>
                    <h1 className={classes.title}>제목</h1>
                    <time className={classes.date}>작성날짜</time>
                </div>

                <p className={classes.content}>
                    내용
                </p>
                
            </article>
            <menu  className={classes.menu}>
                <Link className={classes["edit-btn"]} to ="edit">수정</Link>
            </menu>
        </>
    )
}

export default PostItem;