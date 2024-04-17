import { Feedback, User } from 'types';
import starIcon from 'assets/images/star.png';
import deleteIcon from 'assets/images/trash.png';
import { useCallback, useEffect, useState } from 'react';
import { AxiosRequestConfig } from 'axios';
import { requestBackend } from 'util/requests';
import { getTokenData } from 'util/auth';
import './styles.css';

type Props = {
    feedback: Feedback;
    onDelete: Function;
}

const FeedbackCard = ({feedback, onDelete} : Props) => {

    const [user, setUser] = useState<User | null>(null);

    const getUser = useCallback(async () => {
      try {
        const email = getTokenData()?.user_name;
  
        if (email) {
          const params: AxiosRequestConfig = {
            method: "GET",
            url: `/users/email/${email}`,
            withCredentials: true,
          };
  
          const response = await requestBackend(params);
          setUser(response.data);
        }
      } catch (error) {
        console.log("Error: " + error);
      }
    }, []);
  
    useEffect(() => {
      getUser();
    }, [getUser]);

    const plotStars = (quantity: number) => {
        const arrayImages = Array.from({length: quantity});

        return(
            <div style={{display:"flex", flexDirection:"row"}}>
                {quantity !== 0 ? 
                    arrayImages.map((_) => (
                        <img src={starIcon} alt="" style={{height:"15px", marginRight:"5px"}}/>
                    )) : 
                        <p></p>
                }
            </div>
        );
    };

    const amITheAuthor = () => {
        if(feedback.user.id === user?.id){
            return true;
        }
        else{
            return false;
        }
    }

    const handleDeleteComment = useCallback(() => {
        if(!window.confirm(`Are you sure that you want to delete this feedback?`)){
            return;
        }
        const params : AxiosRequestConfig = {
            method:"DELETE",
            url: `/feedbacks/${feedback.id}`,
            withCredentials:true
          }
          requestBackend(params) 
            .then(response => {
                onDelete();
        })
    }, [feedback.id ,onDelete]);

    function formatDateTime(dateTime: string): string {
        const date = new Date(dateTime);
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const year = date.getFullYear().toString().substr(-2);
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${month}/${day}/${year} ${hours}:${minutes}`;
    }

    return(
        <div className='feedback-card-container'>
            <div className='feedback-card-user-container'>
                <div className='feedback-card-user'>
                    <div className='feedback-card-user-image'>
                        <img src={feedback.user.imgUrl} alt="" />
                    </div>
                    <div className='feedback-card-user-name'>
                        <div className='feedback-card-user-name-time'>
                            <h6>{feedback.user.name}</h6>
                            <p>{formatDateTime(feedback.moment)}</p>
                        </div>
                        <div className='feedback-card-user-name-feedback'>
                            {plotStars(feedback.pontuation)}
                        </div>
                    </div>
                </div>
                {amITheAuthor() && 
                    <div className='feedback-card-delete'>
                        <img src={deleteIcon} alt="" onClick={() => handleDeleteComment()}/>
                    </div>
                }
            </div>
            <div className='feedback-card-comment-container'>
                <p>{feedback.comment}</p>
            </div>
        </div>
    );
}

export default FeedbackCard;