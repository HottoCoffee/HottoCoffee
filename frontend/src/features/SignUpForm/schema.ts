import { z } from "zod";

export const signUpSchema = z.object({
  email: z.string().email("正しいメールアドレス形式で入力してください"),
  password: z.string().min(8, "パスワードは8文字以上である必要があります"),
  account_id: z.string().min(4, "4文字以上のIDを設定してください"),
  display_name: z.string().optional(),
  introduction: z.string().optional(),
  icon_url: z.string().url("正しいURL形式で入力してください").optional(),
});

export type SignUpSchema = z.infer<typeof signUpSchema>;
