import logo from './logo.svg';
import './App.css';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import LoginPage, {action as LoginAction, loader as LoginLoader} from './pages/Login';
import RootLayout, {action as tokenAction} from './pages/Root';
import MainPage from './pages/Main';
import PostDetailPage from './pages/PostDetail';
import { loader as postsLoader} from './pages/Posts';
import PostWritePage from './pages/PostWrite';
import { action as postingAction } from './components/PostForm'
import CategoryPage, { loader as categoryLoader, action as CategoryAction} from './pages/Category';
import { loadPost as postDetailLoader, action as postDeleteAction } from './pages/PostDetail'
import PostUpdatePage from './pages/PostUpdate';
import PostsPage from './pages/Posts';
import SignupPage,{action as SignupAction} from './pages/Signup';
import { tokenLoader } from './util/auth/auth';

const router = createBrowserRouter([
  {
    id : "root",
    path  : '/',
    element : <RootLayout></RootLayout>,
    loader : tokenLoader,
    action : tokenAction,
    children : [
      {
        index : true,
        element : <MainPage></MainPage>
      },
      {
        path : "post",
        children: [
          {
            path : ":postUid",
            id : "post-detail",
            loader : postDetailLoader,
            children :[
              {
                index: true,
                element: <PostDetailPage />,
                action : postDeleteAction
              },
              {
                path : "update",
                element : <PostUpdatePage/>,
                action : postingAction
              }
            ]
          },
          {
            path : "categoty?name=none",
            element : <MainPage></MainPage>
          },
          {
            path : "new",
            element : <PostWritePage></PostWritePage>,
            action : postingAction
          }
        ]
      }
    ]
  },
  {
    path : "posts",
    id: 'posts',
    element : <PostsPage></PostsPage>,
    loader : postsLoader     
  },
  {
    path : "category",
    id : "category",
    element : <CategoryPage></CategoryPage>,
    action : CategoryAction,
    loader : categoryLoader    
  },
  {
    path : "/login",
    element : <LoginPage></LoginPage>,
    action : LoginAction,
    //loader : LoginLoader
  },
  {
    path : "/signup",
    element : <SignupPage></SignupPage>,
    action : SignupAction
  }
])
function App() {
  return <RouterProvider router={router} />;
}

export default App;
