import { Outlet } from "react-router-dom";
import MainNavigation from "../components/MainNavigation";
import Footer from "../components/Footer";

const RootLayout = () => {
    return(
        <>
        <MainNavigation></MainNavigation>
        <main style={{
                    "minHeight": "calc(100vh - 20vh)"
				}}>
            <Outlet></Outlet>
        </main>
        <Footer></Footer>
        </>
    )
}

export default RootLayout;