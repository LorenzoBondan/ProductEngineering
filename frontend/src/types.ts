export type SpringPage<T> = {
    content: T[];
    last: boolean;
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
    first: boolean;
    numberOfElements?: number;
    empty: boolean;
  };


export type User = {
    id:number;
    name: string;
    email: string;
    password: string;
    imgUrl: string;
    roles : Role[];
    feedbacksId: number[];
    recipes: Recipe[];
    favoritesId: number[];
}

export type Role = {
    id: number;
    authority : string;
}

export type Category = {
    id: number;
    name: string;
    recipesId: number[];
}

export type Feedback = {
    id: number;
    comment: string;
    pontuation: number;
    moment: string;
    user: User;
    recipeId: number;
}

export type Recipe = {
    id: number;
    name: string;
    ingredients: string;
    preparation: string;
    time: number;
    imgUrl: string;
    pontuationAverage: number;
    authorId: number;
    feedbacks: Feedback[];
    categories: Category[];
    usersFavoritedId: number[];
}
