import MainSection from "../UI/MainSection";
import PostListContent from "../components/PostListContent";
const MainPage = () => {
    return(
        <>
        <MainSection>
            <PostListContent title="카테고리"></PostListContent>
            <PostListContent title="메모장"></PostListContent>
        </MainSection>

        </>
    )
}

export default MainPage;