import logo from './logo.svg';
import './App.css';
import { RouterProvider, createBrowserRouter } from 'react-router-dom';
import LoginPage from './pages/Login';
import RootLayout from './pages/Root';
import MainPage from './pages/Main';
import PostDetailPage from './pages/PostDetail';
import PostWritePage from './pages/PostWrite';

const router = createBrowserRouter([
  {
    path  : '/',
    element : <RootLayout></RootLayout>,
    children : [
      {
        index : true,
        element : <MainPage></MainPage>
      },
      {
        path : "post",
        children: [
          {
            path : ":postId",
            element: <PostDetailPage/>,
          },
          {
            path : "categoty?name=none",
            element : <MainPage></MainPage>
          },
          {
            path : "write",
            element : <PostWritePage></PostWritePage>,
            
          }
        ]
      },
    ]
    
  },
  {
    path : "/login",
    element : <LoginPage></LoginPage>
  }
])
function App() {
  return <RouterProvider router={router} />;
}

export default App;
