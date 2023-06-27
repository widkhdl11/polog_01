import PageContent from '../UI/PageContent'
import BackGroundImg from '../components/BackGroundImg'
import LoginForm from '../components/LoginForm';

const LoginPage = () => {
    return(
        <>
        <BackGroundImg>
            <PageContent title="Login">
                <LoginForm></LoginForm>
            </PageContent>
        </BackGroundImg>
        </>

    )
}

export default LoginPage;