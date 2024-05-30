import { Post } from "@/api/interfaces";
import { useApi } from "@/api/utils";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-toastify";

type Payload = Omit<Post, "post_id">;

type Response = {};

export const useCreatePost = () => {
  const { api } = useApi();

  return useMutation<Response, Error, Payload>({
    mutationKey: ["CREATE_POST"],
    mutationFn: async (payload) => {
      return api<Payload, Response>(
        {
          path: "/post",
          payload,
        },
        "POST",
      );
    },
    onSuccess: () => {
      toast.success("ポストしました");
    },
  });
};
