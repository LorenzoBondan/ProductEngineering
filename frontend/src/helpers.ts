export const convertDateTime = (dateTime: string) => {
    const dateObj = new Date(dateTime);
    
    const year = dateObj.getFullYear();
    const month = String(dateObj.getMonth() + 1).padStart(2, '0');
    const day = String(dateObj.getDate()).padStart(2, '0');
    
    const formattedDateTime = `${day}/${month}/${year}`;
    
    return formattedDateTime;
};