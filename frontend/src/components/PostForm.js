import classes from './PostForm.module.css'

const PostForm = () => {
    return(
        <>
        <Form method='POST' className={classes.form}>
            <label htmlFor="title">제목</label>
            <input
                id="title"
                type="text"
                name="title"
                required
            //defaultValue={event ? event.title : ''}
            />
            <label htmlFor="content">내용</label>
            <input
                id="content"
                type="text"
                name="content"
                required
            //defaultValue={event ? event.title : ''}
            />

            <button>
                저장
            </button>
        </Form>
        </>
    )
}
export default PostForm;

export const action = async ({request, params}) =>{

    const data = await request.formData();

    const postData = {
        title : data.get('title'),
        content : data.get("content")
    }

    axios({
        method: 'post',
        url: '/api/post/write',
        data: {
          postData
        }
      });
}