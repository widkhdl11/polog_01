import classes from './MainSection.module.css';

function MainSection({children}) {
  return (
    <div className={classes.content}>
      {children}
    </div>
  );
}

export default MainSection;
