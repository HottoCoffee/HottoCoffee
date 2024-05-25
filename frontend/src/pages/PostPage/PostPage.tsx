import { useCreatePost } from "@/api/hooks/useCreatePost";
import { PostForm } from "@/features/PostForm/PostForm";
import { ComponentProps } from "react";
import { useNavigate } from "react-router-dom";

type OnSubmitType = ComponentProps<typeof PostForm>["onSubmit"];

export const PostPage = () => {
  const { mutate } = useCreatePost();
  const navigate = useNavigate();

  const onSubmit: OnSubmitType = (state) => {
    mutate(
      {
        location: state.location,
        origin: state.origin,
        way_to_brew: state.wayToBrew,
        temperature: state.temperature,
        grams_of_coffee: state.gramsOfCoffee,
        grind_size: state.grindSize,
        impression: state.impression,
      },
      {
        onSuccess: () => {
          navigate("/");
        },
      },
    );
  };

  return <PostForm onSubmit={onSubmit} />;
};
