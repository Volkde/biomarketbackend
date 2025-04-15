<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Your Password</title>
</head>
<body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 40px;">
<div style="max-width: 600px; margin: auto; background: #fff; border-radius: 8px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
    <h2 style="color: #333;">Hello ${username},</h2>
    <p style="color: #555;">You recently requested to reset your password. Click the button below to continue:</p>

    <p style="text-align: center; margin: 30px 0;">
        <a href="${resetLink}" style="display: inline-block; padding: 12px 24px; background-color: #28a745; color: #fff; text-decoration: none; border-radius: 5px; font-weight: bold;">
            Reset Password
        </a>
    </p>

    <p style="color: #555;">If the button doesn't work, you can also use the link below:</p>
    <p style="word-break: break-all;"><a href="${resetLink}">${resetLink}</a></p>

    <p style="color: #999;">This link will expire in 10 minutes.</p>
    <p style="color: #999;">If you didn't request a password reset, you can safely ignore this email.</p>

    <hr style="margin-top: 30px; border: none; border-top: 1px solid #eee;">
    <p style="font-size: 12px; color: #aaa;">BioMarketplace Team</p>
</div>
</body>
</html>
