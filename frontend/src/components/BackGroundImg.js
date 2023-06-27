import background from "../accept/login-img.jpg";
import classes from './BackGroundImg.module.css'

function BackGroundImg({children}) {
    return (
        <div 
            style={{ backgroundImage: `url(${background})` }}
            className={classes["bg-section"]}
        >
            {children}
        </div>
    );
  }
  
  export default BackGroundImg;
  