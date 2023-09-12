import { Form, Link, NavLink, useRouteLoaderData } from 'react-router-dom';
import classes from './MainNavigation.module.css'
const MainNavigation = () =>{

    const token = useRouteLoaderData('root');

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
                    { !token ? (
                    <li>
                        <NavLink
                        to="/login"
                        className={({ isActive }) =>
                            isActive ? classes.active : undefined
                        }
                        >
                        authentication
                        </NavLink>
                    </li>
                    ) : 
                    (<li>
                        <Form method='POST'>
                             {/* method='post' 
                             action='/logout' */}
                            <button >Logout</button>
                        </Form>
                    </li>)
                    }
                </ul>
            </nav>
        </header>
    )
}

export default MainNavigation;