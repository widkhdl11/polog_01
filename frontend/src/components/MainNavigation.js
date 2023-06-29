import { Link } from 'react-router-dom';
import classes from './MainNavigation.module.css'
const MainNavigation = () =>{
    return(
        <header className={classes.header}>
            <nav>
                <ul className={classes.list}>
                    <li>
                        메뉴바
                    </li>
                    
                    <li>
                        검색바
                    </li>

                    <li>
                        블로그 이름
                    </li>
                    <li>
                        <Link to ="/post/new">
                            글쓰기
                        </Link>

                    </li>
                </ul>
            </nav>
        </header>
    )
}

export default MainNavigation;