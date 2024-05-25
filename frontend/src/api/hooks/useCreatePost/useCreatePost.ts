import { API_BASE_URL } from "@/api/config";
import { Post } from "@/api/interfaces";
import { useMutation } from "@tanstack/react-query";
import { toast } from "react-toastify";

type Payload = Omit<Post, "post_id">;

type Response = {};

export const useCreatePost = () => {
  return useMutation<Response, Error, Payload>({
    mutationKey: ["CREATE_POST"],
    mutationFn: (payload) => {
      return fetch(`${API_BASE_URL}/post`, {
        method: "POST",
        body: JSON.stringify(payload),
        headers: {
          "Content-Type": "application/json",
        },
      });
    },
    onSuccess: () => {
      toast.success("ポストしました");
    },
  });
};
